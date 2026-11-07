package com.designstudio.ps.dto;

import lombok.Data;

@Data
public class RecommendationDto {
    private String productName;
    private double price;
    private float relevanceScore;
    private String reason;
}