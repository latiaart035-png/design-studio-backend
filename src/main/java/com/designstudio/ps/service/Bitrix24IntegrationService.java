package com.designstudio.ps.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class Bitrix24IntegrationService {

    public Map<String, Object> syncDeal(Long dealId, String bitrix24DealId) {
        log.info("Синхронизация сделки: localId={}, bitrix24DealId={}", dealId, bitrix24DealId);

        // Имитация успешной синхронизации
        return Map.of(
                "status", "SUCCESS",
                "message", "Сделка успешно синхронизирована",
                "synchronizedAt", LocalDateTime.now().toString()
        );
    }
}