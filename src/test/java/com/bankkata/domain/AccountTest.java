package com.bankkata.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Bank Account Operations")
public class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void givenANewAccount_whenItIsCreated_thenItsBalanceShouldBeZero() {
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void givenAnAccount_whenADepositOfPositiveAmountIsMade_thenBalanceShouldIncrease() {
        account.deposit(BigDecimal.valueOf(100.0));
        assertEquals(BigDecimal.valueOf(100.0), account.getBalance());
    }

    @Test
    void givenAnAccount_whenADepositOfNegativeOrZeroAmountIsAttempted_thenAnIllegalArgumentExceptionShouldBeThrownAndBalanceUnchanged() {
        BigDecimal initialBalance = account.getBalance();

        assertThrows(IllegalArgumentException.class, () -> account.deposit(BigDecimal.valueOf(-50.0)));
        assertEquals(initialBalance, account.getBalance());

        assertThrows(IllegalArgumentException.class, () -> account.deposit(BigDecimal.ZERO));
        assertEquals(initialBalance, account.getBalance());
    }


    @Test
    void givenAnAccountWithSufficientBalance_whenAWithdrawalOfPositiveAmountIsMade_thenBalanceShouldDecrease() {
        account.deposit(BigDecimal.valueOf(200.0)); // Initial deposit to have balance
        account.withdraw(BigDecimal.valueOf(50.0));
        assertEquals(BigDecimal.valueOf(150.0), account.getBalance());
    }

    @Test
    void givenAnAccountWithInsufficientBalance_whenAWithdrawalIsAttempted_thenAnIllegalArgumentExceptionShouldBeThrownAndBalanceUnchanged() {
        account.deposit(BigDecimal.valueOf(50.0)); // Initial balance
        BigDecimal initialBalance = account.getBalance();

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(BigDecimal.valueOf(100.0)));
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    void givenAnAccount_whenAWithdrawalOfNegativeOrZeroAmountIsAttempted_thenAnIllegalArgumentExceptionShouldBeThrownAndBalanceUnchanged() {
        account.deposit(BigDecimal.valueOf(100.0)); // Initial balance
        BigDecimal initialBalance = account.getBalance();

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(BigDecimal.valueOf(-50.0)));
        assertEquals(initialBalance, account.getBalance());

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(BigDecimal.ZERO));
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    void givenAnAccount_whenADepositIsMade_thenATransactionShouldBeRecorded() {
        account.deposit(BigDecimal.valueOf(100.0));
        List<Transaction> transactions = account.getTransactions(); // This method will be added
        assertEquals(1, transactions.size());
        Transaction transaction = transactions.getFirst();
        assertEquals(TransactionType.DEPOSIT, transaction.type()); // Using record accessor
        assertEquals(BigDecimal.valueOf(100.0), transaction.amount());
        assertEquals(BigDecimal.valueOf(100.0), transaction.balanceAfterTransaction());
    }

    @Test
    void givenAnAccount_whenAWithdrawalIsMade_thenATransactionShouldBeRecorded() {
        account.deposit(BigDecimal.valueOf(200.0));
        account.withdraw(BigDecimal.valueOf(50.0));
        List<Transaction> transactions = account.getTransactions();
        assertEquals(2, transactions.size()); // Deposit + Withdrawal
        Transaction withdrawalTransaction = transactions.get(1); // Second transaction
        assertEquals(TransactionType.WITHDRAWAL, withdrawalTransaction.type());
        assertEquals(BigDecimal.valueOf(50.0), withdrawalTransaction.amount());
        assertEquals(BigDecimal.valueOf(150.0), withdrawalTransaction.balanceAfterTransaction());
    }
}