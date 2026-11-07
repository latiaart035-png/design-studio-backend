package com.designstudio.ps.dto;

import lombok.Data;
import java.util.List;

@Data
public class BriefResponse {
    private Long id;
    private String status;
    private List<RecommendationDto> recommendations;
}