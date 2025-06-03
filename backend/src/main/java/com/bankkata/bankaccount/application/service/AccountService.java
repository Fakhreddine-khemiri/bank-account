package com.bankkata.bankaccount.application.service;

import com.bankkata.bankaccount.domain.model.Account;
import com.bankkata.bankaccount.domain.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final Account account;
    private final ClockService clockService;

    public AccountService(Account account, ClockService clockService) {
        this.account = account;
        this.clockService = clockService;
    }

    public void deposit(BigDecimal amount) {
        account.deposit(amount, clockService.now());
    }

    public void withdraw(BigDecimal amount) {
        account.withdraw(amount, clockService.now());
    }

    public List<Transaction> getStatementTransactions() {
        return account.getTransactions();
    }

    public BigDecimal getBalance() {
        return account.getBalance();
    }
}