package com.example.demo.controller;

import com.example.demo.controller.request.CreateWalletRequest;
import com.example.demo.controller.request.WalletOperationRequest;
import com.example.demo.dto.WalletDto;
import com.example.demo.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseEntity<WalletDto> performOperation(@PathVariable UUID walletId,
                                                      @RequestBody WalletOperationRequest request) {
        return ResponseEntity.ok(walletService.performOperation(walletId, request.getOperationType(), request.getAmount()));
    }

    @GetMapping("/balance/{walletId}")
    public ResponseEntity<WalletDto> getBalance(@PathVariable UUID walletId) {
        WalletDto walletDto = walletService.getBalance(walletId);
        return ResponseEntity.ok(walletDto);
    }
}