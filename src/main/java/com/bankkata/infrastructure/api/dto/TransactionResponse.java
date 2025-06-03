package com.bankkata.infrastructure.api.dto;

import com.bankkata.domain.model.Transaction;
import com.bankkata.domain.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        LocalDate date,
        TransactionType type,
        BigDecimal amount,
        BigDecimal balance
) {
    public static TransactionResponse fromDomain(Transaction transaction) {
        return new TransactionResponse(
                transaction.date(),
                transaction.type(),
                transaction.amount(),
                transaction.balanceAfterTransaction()
        );
    }
}