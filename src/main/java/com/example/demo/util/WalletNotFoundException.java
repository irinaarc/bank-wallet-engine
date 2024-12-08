package com.example.demo.util;

import java.util.UUID;

public class WalletNotFoundException extends RuntimeException {

    public WalletNotFoundException(UUID walletId) {
        super("Кошелёк не найден с таким ID: " + walletId);
    }
}
