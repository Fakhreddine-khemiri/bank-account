package com.bankkata.domain;

import lombok.Getter;

import java.math.BigDecimal;

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
}