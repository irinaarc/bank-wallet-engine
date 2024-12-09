package com.example.demo.controller.request;

import com.example.demo.entity.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class WalletOperationRequest {
    @NotNull(message = "Тип операции не должен быть пустым")
    private OperationType operationType;

    @NotNull(message = "Сумма не должна быть пустой")
    @Positive(message = "Сумма должна быть положительной и больше нуля")
    private BigDecimal amount;
}