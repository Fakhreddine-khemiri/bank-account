package com.bankkata.bankaccount.infrastructure.api;

import com.bankkata.bankaccount.application.service.AccountService;
import com.bankkata.config.AccountControllerTestConfig;
import com.bankkata.bankaccount.domain.model.Transaction;
import com.bankkata.bankaccount.domain.model.TransactionType;
import com.bankkata.bankaccount.infrastructure.api.dto.DepositRequest;
import com.bankkata.bankaccount.infrastructure.api.dto.TransactionResponse;
import com.bankkata.bankaccount.infrastructure.api.dto.WithdrawalRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc
@AutoConfigureJson
@Import({AccountControllerTestConfig.class, ValidationAutoConfiguration.class})
@DisplayName("Account Controller Integration Tests")
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void resetMocks() {
        reset(accountService);
    }

    @Test
    @DisplayName("POST /deposit - Valid request should return OK and success message")
    void givenValidDepositRequest_whenPostDeposit_thenReturnsOk() throws Exception {
        DepositRequest depositRequest = new DepositRequest(BigDecimal.valueOf(100.00));
        BigDecimal expectedBalanceAfterDeposit = BigDecimal.valueOf(100.00);

        doNothing().when(accountService).deposit(depositRequest.amount());
        when(accountService.getBalance()).thenReturn(expectedBalanceAfterDeposit);

        mockMvc.perform(post("/api/account/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Deposit successful. New balance: " + expectedBalanceAfterDeposit));

        verify(accountService).deposit(depositRequest.amount());
        verify(accountService).getBalance();
    }

    @Test
    @DisplayName("POST /withdrawal - Valid request should return OK and success message")
    void givenValidWithdrawalRequest_whenPostWithdrawal_thenReturnsOk() throws Exception {
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest(BigDecimal.valueOf(50.00));
        BigDecimal balanceAfterWithdrawal = BigDecimal.valueOf(150.00);

        doNothing().when(accountService).withdraw(withdrawalRequest.amount());
        when(accountService.getBalance()).thenReturn(balanceAfterWithdrawal);

        mockMvc.perform(post("/api/account/withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawalRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Withdrawal successful. New balance: " + balanceAfterWithdrawal));

        verify(accountService).withdraw(withdrawalRequest.amount());
        verify(accountService).getBalance();
    }

    @Test
    @DisplayName("GET /balance - Should return OK and current balance")
    void whenGetBalance_thenReturnsOkAndBalance() throws Exception {
        BigDecimal currentBalance = BigDecimal.valueOf(500.00);
        when(accountService.getBalance()).thenReturn(currentBalance);

        mockMvc.perform(get("/api/account/balance")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(currentBalance.doubleValue()));

        verify(accountService).getBalance();
    }


    @Test
    @DisplayName("GET /statement - Should return OK and list of transactions")
    void whenGetStatement_thenReturnsOkAndTransactionList() throws Exception {
        LocalDate date1 = LocalDate.of(2024, 1, 10);
        LocalDate date2 = LocalDate.of(2024, 1, 12);

        List<Transaction> domainTransactions = Arrays.asList(
                new Transaction(date1, TransactionType.DEPOSIT, BigDecimal.valueOf(200.00), BigDecimal.valueOf(200.00)),
                new Transaction(date2, TransactionType.WITHDRAWAL, BigDecimal.valueOf(50.00), BigDecimal.valueOf(150.00))
        );
        when(accountService.getStatementTransactions()).thenReturn(domainTransactions);

        List<TransactionResponse> expectedResponse = domainTransactions.stream()
                .map(TransactionResponse::fromDomain)
                .collect(Collectors.toList());

        mockMvc.perform(get("/api/account/statement")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        verify(accountService).getStatementTransactions();
    }

    @Test
    @DisplayName("GET /statement - Empty list should return OK and empty array")
    void givenNoTransactions_whenGetStatement_thenReturnsOkAndEmptyList() throws Exception {
        when(accountService.getStatementTransactions()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/account/statement")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());

        verify(accountService).getStatementTransactions();
    }

}