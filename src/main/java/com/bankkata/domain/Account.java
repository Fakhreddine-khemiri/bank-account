package com.bankkata.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Account {

    private BigDecimal balance;
    private final List<Transaction> transactions;

    public Account() {
        this.balance = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance = this.balance.add(amount);
        transactions.add(new Transaction(LocalDate.now(), TransactionType.DEPOSIT, amount, this.balance));
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal.");
        }
        this.balance = this.balance.subtract(amount);
        transactions.add(new Transaction(LocalDate.now(), TransactionType.WITHDRAWAL, amount, this.balance));
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}