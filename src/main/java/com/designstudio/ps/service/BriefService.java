package com.designstudio.ps.service;

import com.designstudio.ps.dto.BriefRequest;
import com.designstudio.ps.dto.BriefResponse;
import com.designstudio.ps.dto.RecResult;
import com.designstudio.ps.model.*;
import com.designstudio.ps.repository.*;
import com.designstudio.ps.service.client.AiServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BriefService {

    private final BriefRepository briefRepository;
    private final BriefAnswerRepository briefAnswerRepository;
    private final RecommendationRepository recommendationRepository;
    private final RecommendedProductRepository recommendedProductRepository;
    private final AiServiceClient aiServiceClient;

    @Transactional
    public BriefResponse processBrief(BriefRequest request, User user) {
        if (request.getResponses() == null || request.getResponses().isEmpty()) {
            throw new IllegalArgumentException("Бриф не может быть пустым");
        }

        Brief brief = new Brief();
        brief.setUser(user);
        brief.setStatus(Brief.BriefStatus.SCORING);
        brief.setStylePreference(request.getStylePreference());
        brief.setBudgetRange(request.getBudgetRange());
        final Brief savedBrief = briefRepository.save(brief);

        request.getResponses().forEach((code, text) -> {
            BriefAnswer answer = new BriefAnswer();
            answer.setBrief(savedBrief);
            answer.setQuestionCode(code);
            answer.setAnswerText(text);
            briefAnswerRepository.save(answer);
        });

        RecResult result;
        try {
            result = aiServiceClient.requestScoring(request);
        } catch (Exception e) {
            log.warn("Ошибка при вызове ИИ-сервиса: {}", e.getMessage());
            result = new RecResult("FALLBACK", 0.5f, List.of(), "Ошибка ИИ");
            savedBrief.setStatus(Brief.BriefStatus.FALLBACK_MODE);
        }

        Recommendation recommendation = new Recommendation();
        recommendation.setUser(user);
        recommendation.setDeal(null);
        final Recommendation savedRecommendation = recommendationRepository.save(recommendation);

        if (result.getRecommendations() != null) {
            List<RecommendedProduct> products = result.getRecommendations().stream()
                    .map(dto -> {
                        RecommendedProduct rp = new RecommendedProduct();
                        rp.setRecommendation(savedRecommendation);
                        rp.setProductName(dto.getProductName());
                        rp.setPrice(dto.getPrice());
                        rp.setRelevanceScore(dto.getRelevanceScore());
                        rp.setReason(dto.getReason());
                        return rp;
                    })
                    .collect(Collectors.toList());
            recommendedProductRepository.saveAll(products);
        }

        if (savedBrief.getStatus() != Brief.BriefStatus.FALLBACK_MODE) {
            savedBrief.setStatus(Brief.BriefStatus.SCORED);
        }
        briefRepository.save(savedBrief);

        BriefResponse response = new BriefResponse();
        response.setId(savedBrief.getId());
        response.setStatus(savedBrief.getStatus().name());
        response.setRecommendations(result.getRecommendations());
        return response;
    }
}