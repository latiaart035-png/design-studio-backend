package com.designstudio.ps.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Map;

@Data
public class BriefRequest {
    @NotBlank
    private String roomType;

    @NotBlank
    private String stylePreference;

    @NotBlank
    private String budgetRange;

    @NotNull
    private Map<String, String> responses;
}