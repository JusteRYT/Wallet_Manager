package com.example.wallet.controller;

import com.example.wallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * REST-контроллер для управления кошельками.
 * Предоставляет эндпоинты для получения баланса и выполнения операций.
 */
@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * Получает баланс указанного кошелька.
     *
     * @param walletId идентификатор кошелька
     * @return баланс кошелька
     */
    @GetMapping("/{walletId}")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable UUID walletId) {
        try{
            double balance = walletService.getBalance(walletId);
            return ResponseEntity.ok(Map.of("walletId", walletId, "balance", balance));
        } catch (IllegalArgumentException e){
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
        try {
            UUID walletId = UUID.fromString((String) request.get("walletId"));
            String operationType = (String) request.get("operationType");
            double amount = Double.parseDouble(request.get("amount").toString());

            walletService.perfomOperation(walletId, operationType, amount);

            return ResponseEntity.ok(Map.of("walletId", walletId, "operation", operationType, "amount", amount));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }



}
