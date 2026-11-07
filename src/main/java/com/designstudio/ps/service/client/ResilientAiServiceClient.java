package com.designstudio.ps.service.client;

import com.designstudio.ps.dto.BriefRequest;
import com.designstudio.ps.dto.RecResult;
import com.designstudio.ps.dto.RecommendationDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ResilientAiServiceClient implements AiServiceClient {

    @Override
    @CircuitBreaker(name = "aiService", fallbackMethod = "fallbackScoring")
    public RecResult requestScoring(BriefRequest request) {
        log.info("Отправка запроса в AI-сервис (имитация)...");

        RecommendationDto rec1 = new RecommendationDto();
        rec1.setProductName("Кресло Loop");
        rec1.setPrice(85000.0);
        rec1.setRelevanceScore(0.92f);
        rec1.setReason("Соответствует скандинавскому стилю и бюджету");

        RecommendationDto rec2 = new RecommendationDto();
        rec2.setProductName("Стол из дуба");
        rec2.setPrice(63500.0);
        rec2.setRelevanceScore(0.85f);
        rec2.setReason("Натуральные материалы, подходит под бюджет");

        return new RecResult("SUCCESS", 0.92f, List.of(rec1, rec2), "Рекомендации сформированы");
    }

    public RecResult fallbackScoring(BriefRequest request, Throwable t) {
        log.error("AI-сервис недоступен. Причина: {}. Возврат базовых рекомендаций.", t.getMessage());
        RecommendationDto fallback = new RecommendationDto();
        fallback.setProductName("Базовый набор мебели");
        fallback.setPrice(50000.0);
        fallback.setRelevanceScore(0.5f);
        fallback.setReason("Сервис временно недоступен. Базовые рекомендации.");

        return new RecResult("FALLBACK", 0.5f, List.of(fallback), "Сервис временно недоступен");
    }
}