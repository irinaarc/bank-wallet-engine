package com.example.demo.controller;

import com.example.demo.controller.request.CreateWalletRequest;
import com.example.demo.controller.request.WalletOperationRequest;
import com.example.demo.dto.WalletDto;
import com.example.demo.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/api/v1/wallet")
@Slf4j
@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    private final ConversionService conversionService;

    @PostMapping
    public UUID createWallet(@RequestBody CreateWalletRequest request) {
        UUID id = walletService.createWallet(conversionService.convert(request, WalletDto.class));
        log.info("Пользователь c id: " + id + " успешно сохранен");
        return id;
    }

    @PostMapping("/operation/{walletId}")
    public CompletableFuture<ResponseEntity<WalletDto>> performOperation(@PathVariable UUID walletId,
                                                                         @RequestBody WalletOperationRequest request) {
        return walletService.performOperation(walletId, request.getOperationType(), request.getAmount())
                .thenApply(walletDto -> ResponseEntity.ok(walletDto))
                .exceptionally(ex -> {
                    // Обработка ошибок, например, 500 Internal Server Error
                    log.error("Error performing operation: {}", ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @GetMapping("/balance/{walletId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable UUID walletId) {
        BigDecimal balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }
}