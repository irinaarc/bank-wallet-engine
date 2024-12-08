package com.example.demo.conversion;

import com.example.demo.controller.request.CreateWalletRequest;
import com.example.demo.dto.WalletDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class CreateWalletRequestToWalletDto implements Converter<CreateWalletRequest, WalletDto> {
    @Override
    public WalletDto convert(CreateWalletRequest source) {
        return WalletDto.builder()
                .phoneNumber(source.getPhoneNumber())
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
