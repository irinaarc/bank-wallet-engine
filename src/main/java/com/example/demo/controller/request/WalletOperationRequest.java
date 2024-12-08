package com.example.demo.controller.request;

import com.example.demo.entity.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletOperationRequest {
    private OperationType operationType;
    private BigDecimal amount;
}