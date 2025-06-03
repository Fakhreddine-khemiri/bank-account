package com.bankkata.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Account Domain Operations (Unit Tests)")
public class AccountTest {

    private Account account;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        account = new Account();
        testDate = LocalDate.of(2024, 1, 1);
    }

    @Test
    void givenANewAccount_whenItIsCreated_thenItsBalanceShouldBeZero() {
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void givenAnAccount_whenADepositOfPositiveAmountIsMade_thenBalanceShouldIncrease() {
        account.deposit(BigDecimal.valueOf(100.0), testDate);
        assertEquals(BigDecimal.valueOf(100.0), account.getBalance());
    }

    @Test
    void givenAnAccount_whenADepositOfNegativeOrZeroAmountIsAttempted_thenAnIllegalArgumentExceptionShouldBeThrownAndBalanceUnchanged() {
        BigDecimal initialBalance = account.getBalance();

        assertThrows(IllegalArgumentException.class, () -> account.deposit(BigDecimal.valueOf(-50.0), testDate));
        assertEquals(initialBalance, account.getBalance());

        assertThrows(IllegalArgumentException.class, () -> account.deposit(BigDecimal.ZERO, testDate));
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    void givenAnAccountWithSufficientBalance_whenAWithdrawalOfPositiveAmountIsMade_thenBalanceShouldDecrease() {
        account.deposit(BigDecimal.valueOf(200.0), testDate);
        account.withdraw(BigDecimal.valueOf(50.0), testDate);
        assertEquals(BigDecimal.valueOf(150.0), account.getBalance());
    }

    @Test
    void givenAnAccountWithInsufficientBalance_whenAWithdrawalIsAttempted_thenAnIllegalArgumentExceptionShouldBeThrownAndBalanceUnchanged() {
        account.deposit(BigDecimal.valueOf(50.0), testDate);
        BigDecimal initialBalance = account.getBalance();

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(BigDecimal.valueOf(100.0), testDate));
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    void givenAnAccount_whenAWithdrawalOfNegativeOrZeroAmountIsAttempted_thenAnIllegalArgumentExceptionShouldBeThrownAndBalanceUnchanged() {
        account.deposit(BigDecimal.valueOf(100.0), testDate);
        BigDecimal initialBalance = account.getBalance();

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(BigDecimal.valueOf(-50.0), testDate));
        assertEquals(initialBalance, account.getBalance());

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(BigDecimal.ZERO, testDate));
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    void givenAnAccount_whenADepositIsMade_thenATransactionShouldBeRecordedWithCorrectData() {
        account.deposit(BigDecimal.valueOf(100.0), testDate);
        List<Transaction> transactions = account.getTransactions();
        assertEquals(1, transactions.size());
        Transaction transaction = transactions.getFirst();
        assertEquals(testDate, transaction.date());
        assertEquals(TransactionType.DEPOSIT, transaction.type());
        assertEquals(BigDecimal.valueOf(100.0), transaction.amount());
        assertEquals(BigDecimal.valueOf(100.0), transaction.balanceAfterTransaction());
    }

    @Test
    void givenAnAccount_whenAWithdrawalIsMade_thenATransactionShouldBeRecordedWithCorrectData() {
        account.deposit(BigDecimal.valueOf(200.0), testDate);
        account.withdraw(BigDecimal.valueOf(50.0), testDate);
        List<Transaction> transactions = account.getTransactions();
        assertEquals(2, transactions.size());
        Transaction withdrawalTransaction = transactions.get(1);
        assertEquals(testDate, withdrawalTransaction.date());
        assertEquals(TransactionType.WITHDRAWAL, withdrawalTransaction.type());
        assertEquals(BigDecimal.valueOf(50.0), withdrawalTransaction.amount());
        assertEquals(BigDecimal.valueOf(150.0), withdrawalTransaction.balanceAfterTransaction());
    }
}