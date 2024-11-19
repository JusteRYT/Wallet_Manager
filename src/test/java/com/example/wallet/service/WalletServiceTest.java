package com.example.wallet.service;

import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletServiceTest {

    private WalletRepository walletRepository;
    private WalletService walletService;

    @BeforeEach
    void setUp() {
        walletRepository = mock(WalletRepository.class);
        walletService = new WalletService(walletRepository);
    }

    @Test
    void getBalance_ShouldReturnCorrectBalance_WhenWalletExists() {

        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId, 5000.0);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        double balance = walletService.getBalance(walletId);

        assertEquals(5000.0, balance);
    }

    @Test
    void getBalance_ShouldReturnCorrectBalance_WhenWalletNotFound() {
        UUID walletId = UUID.randomUUID();
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> walletService.getBalance(walletId));
        assertEquals("Wallet not found", exception.getMessage());
    }

    @Test
    void performOperation_ShouldDeposit_WHenOperationIsDeposit() {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId, 1000.0);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        walletService.performOperation(walletId, "DEPOSIT", 500.0);

        assertEquals(1500.0, wallet.getBalance());
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void performOperation_ShouldWithdraw_WHenOperationIsWithdraw() {

        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId, 2000.0);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        walletService.performOperation(walletId, "WITHDRAW", 500.0);

        assertEquals(1500.0, wallet.getBalance());
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void performOperation_ShouldThrowException_WhenInsufficientFunds() {

        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId, 300.0);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> walletService.performOperation(walletId, "WITHDRAW", 500.0));

        assertEquals("Not enough balance", exception.getMessage());
        verify(walletRepository, never()).save(wallet);
    }

    @Test
    void performOperation_ShouldThrowException_WhenOperationInvalid() {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId, 1000.0);
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> walletService.performOperation(walletId, "INVALID", 500.0));

        assertEquals("Invalid operation type", exception.getMessage());
        verify(walletRepository, never()).save(wallet);  // save не должен быть вызван при невалидной операции
    }
}
