package com.example.wallet.model;

import jakarta.persistence.*;

import java.util.UUID;

/**
 * Сущность, представляющая кошелек.
 * Хранит информацию о текущем балансе.
 */
@Entity
@Table(name = "wallet")
public class Wallet {

    public Wallet(UUID uuid, double balance) {
        this.id = uuid;
        this.balance = balance;
    }

    /**
     * Уникальный идентификатор кошелька.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Текущий баланс кошелька.
     */
    @Column(nullable = false)
    private double balance;

    public Wallet() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
