package com.example.demo.service;

import com.example.demo.dto.WalletDto;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface WalletService {

    UUID createWallet(WalletDto walletDto);
//
//            performOperation
//
//    getBalance
}
