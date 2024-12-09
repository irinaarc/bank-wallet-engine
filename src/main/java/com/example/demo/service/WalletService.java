package com.example.demo.service;

import com.example.demo.dto.WalletDto;
import com.example.demo.entity.OperationType;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


public interface WalletService {

    UUID createWallet(WalletDto walletDto);

    CompletableFuture<WalletDto> performOperation(UUID walletId, OperationType operationType, BigDecimal amount);

    BigDecimal getBalance(UUID walletId);
}
