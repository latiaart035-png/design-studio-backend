package com.designstudio.ps.controller;

import com.designstudio.ps.dto.RecommendationDto;
import com.designstudio.ps.model.RecommendedProduct;
import com.designstudio.ps.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationRepository recommendationRepository;

    @GetMapping
    public ResponseEntity<List<RecommendationDto>> getRecommendations(@RequestParam Long clientId) {
        List<RecommendationDto> recommendations = recommendationRepository.findByUserId(clientId)
                .stream()
                .flatMap(rec -> rec.getProducts().stream())
                .map(this::convertToDto)
                .collect(Collectors.toList());

        if (recommendations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recommendations);
    }

    private RecommendationDto convertToDto(RecommendedProduct product) {
        RecommendationDto dto = new RecommendationDto();
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        dto.setRelevanceScore(product.getRelevanceScore());
        dto.setReason(product.getReason());
        return dto;
    }
}