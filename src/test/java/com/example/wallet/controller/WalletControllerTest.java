package com.example.wallet.controller;

import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тестовый класс для контроллера кошельков (WalletController).
 * Проверяет работу HTTP-эндпоинтов для получения баланса и выполнения операций.
 */
@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @MockBean
    private WalletRepository walletRepository;

    /**
     * Тестирует получение баланса кошелька, если кошелёк существует.
     * Проверяет, что возвращается правильный баланс.
     */
    @Test
    void getBalance_ShouldReturnBalance_WhenWalletExists() throws Exception {

        UUID walletId = UUID.randomUUID();
        double balance = 5000.0;
        when(walletService.getBalance(walletId)).thenReturn(balance);

        mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(balance));
    }

    /**
     * Тестирует получение баланса кошелька, если кошелёк не найден.
     * Ожидается ответ с кодом 404 и сообщением об ошибке.
     */
    @Test
    void getBalance_ShouldReturnBalance_WhenWalletDoesNotExist() throws Exception {

        UUID walletId = UUID.randomUUID();
        when(walletService.getBalance(walletId)).thenThrow(new IllegalArgumentException("Wallet not found"));
        mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Wallet not found"));
    }

    /**
     * Тестирует успешное выполнение операции, если входные данные корректны.
     * Проверяется, что ответ содержит данные о кошельке и операции.
     */
    @Test
    void performOperation_ShouldReturnSuccess_WhenOperationValid() throws Exception {
        UUID walletId = UUID.randomUUID();
        String operationType = "DEPOSIT";
        double amount = 1000.0;

        Mockito.doNothing().when(walletService).performOperation(walletId, operationType, amount);

        String requestBody = String.format("""
                {
                  "walletId": "%s",
                  "operationType": "%s",
                  "amount": %s
                }
                """, walletId, operationType, amount);

        mockMvc.perform(post("/api/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletId.toString()))
                .andExpect(jsonPath("$.operation").value(operationType))
                .andExpect(jsonPath("$.amount").value(amount));
    }

    /**
     * Тестирует ситуацию с некорректными входными данными.
     * Проверяется, что возвращается ошибка с кодом 400.
     */
    @Test
    void performOperation_ShouldReturnError_WhenInputInvalid() throws Exception {
        String invalidRequestBody = """
                {
                  "walletId": "invalid-uuid",
                  "operationType": "DEPOSIT",
                  "amount": 1000
                }
                """;

        mockMvc.perform(post("/api/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}
