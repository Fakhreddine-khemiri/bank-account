package com.bankkata.domain.service;

import com.bankkata.domain.model.Account;
import com.bankkata.domain.model.Transaction;
import com.bankkata.domain.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Account Service Operations (Integration Tests)")
public class AccountServiceTest {

    @Mock
    private ClockService clockService;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        Account account = new Account();
        accountService = new AccountService(account, clockService);
    }

    @Test
    void givenAnAccount_whenADepositIsMadeViaService_thenTransactionIsRecordedWithCorrectDate() {
        LocalDate fixedDate = LocalDate.of(2024, 5, 20);
        when(clockService.now()).thenReturn(fixedDate);

        accountService.deposit(BigDecimal.valueOf(100.0));

        List<Transaction> transactions = accountService.getStatementTransactions();
        assertEquals(1, transactions.size());
        Transaction transaction = transactions.getFirst();
        assertEquals(fixedDate, transaction.date());
        assertEquals(TransactionType.DEPOSIT, transaction.type());
        assertEquals(BigDecimal.valueOf(100.0), transaction.amount());
        assertEquals(BigDecimal.valueOf(100.0), transaction.balanceAfterTransaction());
    }

    @Test
    void givenAnAccount_whenAWithdrawalIsMadeViaService_thenTransactionIsRecordedWithCorrectDate() {
        LocalDate depositDate = LocalDate.of(2024, 5, 20);
        LocalDate withdrawalDate = LocalDate.of(2024, 5, 21);

        when(clockService.now())
                .thenReturn(depositDate)
                .thenReturn(withdrawalDate);

        accountService.deposit(BigDecimal.valueOf(200.0));
        accountService.withdraw(BigDecimal.valueOf(50.0));

        List<Transaction> transactions = accountService.getStatementTransactions();
        assertEquals(2, transactions.size());

        Transaction withdrawalTransaction = transactions.get(1);
        assertEquals(withdrawalDate, withdrawalTransaction.date());
        assertEquals(TransactionType.WITHDRAWAL, withdrawalTransaction.type());
        assertEquals(BigDecimal.valueOf(50.0), withdrawalTransaction.amount());
        assertEquals(BigDecimal.valueOf(150.0), withdrawalTransaction.balanceAfterTransaction());
    }
}