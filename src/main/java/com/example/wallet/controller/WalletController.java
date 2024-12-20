package com.example.wallet.controller;

import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * REST-контроллер для управления кошельками.
 * Предоставляет эндпоинты для получения баланса и выполнения операций.
 */
@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;
    private final WalletRepository walletRepository;

    public WalletController(WalletService walletService, WalletRepository walletRepository) {
        this.walletService = walletService;
        this.walletRepository = walletRepository;
    }

    /**
     * Получает баланс указанного кошелька.
     *
     * @param walletId идентификатор кошелька
     * @return баланс кошелька
     */
    @GetMapping("/{walletId}")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable UUID walletId) {
        try {
            double balance = walletService.getBalance(walletId);
            return ResponseEntity.ok(Map.of("walletId", walletId, "balance", balance));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Выполняет операцию изменения баланса (пополнение или снятие).
     *
     * @param request тело запроса с информацией об операции
     * @return статус выполнения операции
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> performOperation(@RequestBody Map<String, Object> request) {
        UUID walletId = UUID.fromString((String) request.get("walletId"));
        String operationType = (String) request.get("operationType");
        double amount = Double.parseDouble(request.get("amount").toString());

        walletService.performOperationAsync(walletId, operationType, amount);

        // Возвращаем успешный ответ с данными
        return ResponseEntity.accepted().body(Map.of(
                "walletId", walletId.toString(),
                "operation", operationType,
                "amount", amount
        ));
    }
}
