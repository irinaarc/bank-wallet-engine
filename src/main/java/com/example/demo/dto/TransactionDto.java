package com.example.demo.dto;

import com.example.demo.entity.OperationType;
import com.example.demo.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    private UUID id;

    private Wallet wallet;

    private OperationType operationType;

    private BigDecimal amount;
}
