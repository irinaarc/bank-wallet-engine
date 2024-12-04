package com.example.demo.controller;

import com.example.demo.controller.request.CreateWalletRequest;
import com.example.demo.dto.WalletDto;
import com.example.demo.entity.Wallet;
import com.example.demo.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

//    @PostMapping("/{walletId}/operation")
//    public ResponseEntity<WalletDto> performOperation(@PathVariable UUID walletId,
//                                                      @RequestParam String operationType,
//                                                      @RequestParam BigDecimal amount) {
//        return ResponseEntity.ok(walletService.performOperation(walletId, operationType, amount));
//    }
//
//    @GetMapping("/{walletId}/balance")
//    public ResponseEntity<WalletDto> getBalance(@PathVariable UUID walletId) {
//        return ResponseEntity.ok(walletService.getBalance(walletId));
//    }
}

