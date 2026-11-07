package com.designstudio.ps.controller;

import com.designstudio.ps.service.Bitrix24IntegrationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/integration/bitrix24")
@RequiredArgsConstructor
public class Bitrix24Controller {

    private final Bitrix24IntegrationService bitrix24IntegrationService;

    @PostMapping("/sync")
    public ResponseEntity<Map<String, Object>> syncDeal(@RequestBody SyncRequest request) {
        Map<String, Object> result = bitrix24IntegrationService.syncDeal(
                request.getDealId(),
                request.getBitrix24DealId()
        );
        return ResponseEntity.ok(result);
    }

    @Data
    public static class SyncRequest {
        private Long dealId;
        private String bitrix24DealId;
    }
}