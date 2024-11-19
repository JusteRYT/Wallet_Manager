package com.example.wallet.repository;

import com.example.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью Wallet.
 * Предоставляет базовые методы CRUD.
 */
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
