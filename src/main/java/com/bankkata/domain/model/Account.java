package com.bankkata.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {

    @Getter
    private BigDecimal balance;
    private final List<Transaction> transactions;

    public Account() {
        this.balance = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void deposit(BigDecimal amount, LocalDate date) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be strictly positive.");
        }
        this.balance = this.balance.add(amount);
        transactions.add(new Transaction(date, TransactionType.DEPOSIT, amount, this.balance));
    }

    public void withdraw(BigDecimal amount, LocalDate date) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be strictly positive.");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds. Current balance: " + this.balance + ", attempted withdrawal: " + amount + ".");
        }
        this.balance = this.balance.subtract(amount);
        transactions.add(new Transaction(date, TransactionType.WITHDRAWAL, amount, this.balance));
    }

}