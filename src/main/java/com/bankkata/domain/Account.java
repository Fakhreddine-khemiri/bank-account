package com.bankkata.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
public class Account {

    private BigDecimal balance;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal.");
        }
        this.balance = this.balance.subtract(amount);
    }

    public List<Transaction> getTransactions() {
        return List.of(new Transaction(LocalDate.now(), TransactionType.DEPOSIT, BigDecimal.ZERO, BigDecimal.ZERO));
    }
}