package com.example.wallet.repository;

import com.example.wallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью Transaction.
 * Предоставляет базовые методы CRUD.
 */
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
