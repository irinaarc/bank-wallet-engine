package com.example.demo.service;

import com.example.demo.dto.WalletDto;
import com.example.demo.entity.OperationType;

import java.math.BigDecimal;
import java.util.UUID;


public interface WalletService {

    UUID createWallet(WalletDto walletDto);

    WalletDto performOperation(UUID walletId, OperationType operationType, BigDecimal amount);

    BigDecimal getBalance(UUID walletId);
}
