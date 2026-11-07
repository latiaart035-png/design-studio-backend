package com.designstudio.ps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecResult {
    private String status;
    private Float score;
    private List<RecommendationDto> recommendations;
    private String message;
}