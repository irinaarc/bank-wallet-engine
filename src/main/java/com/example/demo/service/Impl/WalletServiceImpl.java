package com.example.demo.service.Impl;

import com.example.demo.dto.WalletDto;
import com.example.demo.entity.Wallet;
import com.example.demo.repository.WalletRepository;
import com.example.demo.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Setter
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ConversionService conversionService;
    @Override
    @Transactional
    public UUID createWallet(WalletDto walletDto) {
        log.info("Попытка сохранить Кошелёк");
        Wallet wallet = Wallet.builder()
                .phoneNumber(walletDto.getPhoneNumber())
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();

        return walletRepository.save(wallet).getId();
    }
}
