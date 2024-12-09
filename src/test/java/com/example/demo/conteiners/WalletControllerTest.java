package com.example.demo.conteiners;

import com.example.demo.controller.WalletController;
import com.example.demo.controller.request.CreateWalletRequest;
import com.example.demo.controller.request.WalletOperationRequest;
import com.example.demo.dto.WalletDto;
import com.example.demo.entity.OperationType;
import com.example.demo.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WalletControllerTest {

    @InjectMocks
    private WalletController walletController;

    @Mock
    private ConversionService conversionService;

    @Mock
    private WalletService walletService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateWallet() {
        CreateWalletRequest request = new CreateWalletRequest("123-456-7890");
        UUID walletId = UUID.randomUUID();
        when(walletService.createWallet(any())).thenReturn(walletId);

        UUID result = walletController.createWallet(request);

        assertEquals(walletId, result);
        verify(walletService, times(1)).createWallet(any());
    }

    @Test
    public void testPerformOperation() throws ExecutionException, InterruptedException {
        WalletOperationRequest request = new WalletOperationRequest(OperationType.DEPOSIT,
                BigDecimal.valueOf(100));
        UUID walletId = UUID.randomUUID();
        WalletDto walletDto = new WalletDto(walletId, "123-456-7890",
                BigDecimal.valueOf(100), null);

        when(walletService.performOperation(walletId, OperationType.DEPOSIT, BigDecimal.valueOf(100)))
                .thenReturn(CompletableFuture.completedFuture(walletDto));

        CompletableFuture<ResponseEntity<WalletDto>> responseFuture = walletController.performOperation(walletId, request);

        ResponseEntity<WalletDto> response = responseFuture.get();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(walletDto, response.getBody());
        verify(walletService, times(1)).performOperation(walletId, OperationType.DEPOSIT, BigDecimal.valueOf(100));
    }

    @Test
    public void testGetBalance() {
        UUID walletId = UUID.randomUUID();
        BigDecimal balance = BigDecimal.valueOf(100);
        when(walletService.getBalance(walletId)).thenReturn(balance);

        ResponseEntity<BigDecimal> response = walletController.getBalance(walletId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(balance, response.getBody());
        verify(walletService, times(1)).getBalance(walletId);
    }
}