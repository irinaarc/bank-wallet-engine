package com.example.demo.service.Impl;

import com.example.demo.dto.WalletDto;
import com.example.demo.entity.OperationType;
import com.example.demo.entity.Wallet;
import com.example.demo.repository.WalletRepository;
import com.example.demo.service.WalletService;
import com.example.demo.util.InvalidOperationTypeException;
import com.example.demo.util.WalletNotFoundException;
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

    @Override
    @Transactional
    public WalletDto performOperation(UUID walletId, OperationType operationType, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        switch (operationType) {
            case DEPOSIT:
                wallet.setBalance(wallet.getBalance().add(amount));
                break;
            case WITHDRAW:
                if (wallet.getBalance().compareTo(amount) < 0) {
                    throw new RuntimeException("Недостаточно средств для совершения операции");
                }
                wallet.setBalance(wallet.getBalance().subtract(amount));
                break;
            default:
                throw new InvalidOperationTypeException("Некорректный тип операции: " + operationType +
                        ". Доступные операции: DEPOSIT, WITHDRAW.");
        }

        walletRepository.save(wallet);
        return WalletDto.builder()
                .id(wallet.getId())
                .phoneNumber(wallet.getPhoneNumber())
                .balance(wallet.getBalance())
                .createdAt(wallet.getCreatedAt())
                .build();
    }


    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
        return wallet.getBalance();
    }
}
