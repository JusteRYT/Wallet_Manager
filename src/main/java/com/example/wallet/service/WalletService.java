package com.example.wallet.service;

import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
     * @param walletId идентификатор кошелька
     * @param operationType тип операции: DEPOSIT или WITHDRAW
     * @param amount сумма операции
     * @throws IllegalArgumentException если кошелек не найден или недостаточно средств
     */
    public void perfomOperation(UUID walletId, String operationType, double amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        if("DEPOSIT".equalsIgnoreCase(operationType)) {
            wallet.setBalance(wallet.getBalance() + amount);
        } else if("WITHDRAW".equalsIgnoreCase(operationType)) {
            wallet.setBalance(wallet.getBalance() - amount);
        } else {
            throw new IllegalArgumentException("Invalid operation type");
        }

        walletRepository.save(wallet);
    }
}
