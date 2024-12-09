package com.example.demo.service.Impl;

import com.example.demo.dto.WalletDto;
import com.example.demo.entity.OperationType;
import com.example.demo.entity.Wallet;
import com.example.demo.repository.WalletRepository;
import com.example.demo.service.WalletService;
import com.example.demo.util.InsufficientFundsException;
import com.example.demo.util.InvalidOperationTypeException;
import com.example.demo.util.WalletNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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

    @Async
    @Override
    @Transactional
    public CompletableFuture<WalletDto> performOperation(UUID walletId,
                                                         OperationType operationType, BigDecimal amount) {
        log.info("Попытка выполнить операцию {} на кошельке с ID: {} на сумму: {}", operationType, walletId, amount);
        try {
            Wallet wallet = walletRepository.findById(walletId)
                    .orElseThrow(() -> {
                                log.error("Кошелёк с ID: {} не найден", walletId);
                                return new WalletNotFoundException(walletId);
                            }
                    );

            switch (operationType) {
                case DEPOSIT:
                    wallet.setBalance(wallet.getBalance().add(amount));
                    log.info("Пополнение кошелька с ID: {} на сумму: {}", walletId, amount);
                    break;
                case WITHDRAW:
                    if (wallet.getBalance().compareTo(amount) < 0) {
                        log.error("Недостаточно средств для операции снятия с кошелька с ID: {}. Баланс: {}, " +
                                "Запрашиваемая сумма: {}", walletId, wallet.getBalance(), amount);
                        throw new InsufficientFundsException("Недостаточно средств для совершения операции");
                    }
                    wallet.setBalance(wallet.getBalance().subtract(amount));
                    log.info("Снятие средств с кошелька с ID: {} на сумму: {}", walletId, amount);
                    break;
                default:
                    log.error("Некорректный тип операции: {}", operationType);
                    throw new InvalidOperationTypeException("Некорректный тип операции: " + operationType);
            }

            walletRepository.save(wallet);
            WalletDto walletDto = WalletDto.builder()
                    .id(wallet.getId())
                    .phoneNumber(wallet.getPhoneNumber())
                    .balance(wallet.getBalance())
                    .createdAt(wallet.getCreatedAt())
                    .build();

            return CompletableFuture.completedFuture(walletDto);

        } catch (OptimisticLockingFailureException e) {
            throw new RuntimeException("Конфликт при записи, попробуйте снова", e);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
        return wallet.getBalance();
    }
}
