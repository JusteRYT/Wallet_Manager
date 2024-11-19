package com.example.wallet.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность, представляющая транзакцию.
 * Хранит информацию о типе операции, её сумме и времени создания.
 */
@Entity
@Table(name = "transaction")
public class Transaction {

    /**
     * Уникальный идентификатор транзакции.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Идентификатор кошелька, к которому относится транзакция.
     */
    @Column(nullable = false)
    private UUID walletId;

    /**
     * Тип операции: DEPOSIT или WITHDRAW.
     */
    @Column(nullable = false)
    private String type;

    /**
     * Сумма транзакции.
     */
    @Column(nullable = false)
    private double amount;

    /**
     * Время создания транзакции.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
