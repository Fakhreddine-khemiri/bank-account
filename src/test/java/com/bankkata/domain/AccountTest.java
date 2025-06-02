package com.bankkata.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

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
}