package com.example.wallet.service;

import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Сервис для управления кошельками.
 * Обрабатывает бизнес-логику работы с кошельками.
 */
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

     /**
     * Выполняет асинхронную операцию изменения баланса кошелька.
     *
     * @param walletId      идентификатор кошелька
     * @param operationType тип операции: DEPOSIT или WITHDRAW
     * @param amount        сумма операции
     * @return CompletableFuture<Void>
     */
    @Async
    public CompletableFuture<Void> performOperationAsync(UUID walletId, String operationType, double amount) {
        performOperation(walletId, operationType, amount);
        return CompletableFuture.completedFuture(null);
    }
    /**
     * Получает баланс кошелька по его идентификатору.
     *
     * @param walletId идентификатор кошелька
     * @return баланс кошелька
     * @throws IllegalArgumentException если кошелек не найден
     */
    public double getBalance(UUID walletId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        return wallet.orElseThrow(() -> new IllegalArgumentException("Wallet not found")).getBalance();
    }

    /**
     * Выполняет операцию изменения баланса кошелька.
     *
     * @param walletId      идентификатор кошелька
     * @param operationType тип операции: DEPOSIT или WITHDRAW
     * @param amount        сумма операции
     * @throws IllegalArgumentException если кошелек не найден или недостаточно средств
     */
    public void performOperation(UUID walletId, String operationType, double amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        switch (operationType.toUpperCase()) {
            case "WITHDRAW":
                if (wallet.getBalance() < amount) {
                    throw new IllegalArgumentException("Not enough balance");
                }
                wallet.setBalance(wallet.getBalance() - amount);
                break;
            case "DEPOSIT":
                wallet.setBalance(wallet.getBalance() + amount);
                break;
            default:
                throw new IllegalArgumentException("Invalid operation type");
        }
        walletRepository.save(wallet);
    }
}
