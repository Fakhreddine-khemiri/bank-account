package com.bankkata.bankaccount.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a single transaction on a bank account.
 * This is a Java Record, ideal for immutable data carriers.
 *
 * @param date                    The date of the transaction.
 * @param type                    The type of transaction (DEPOSIT or WITHDRAWAL).
 * @param amount                  The amount of the transaction.
 * @param balanceAfterTransaction The account balance immediately after this transaction.
 */
public record Transaction(LocalDate date, TransactionType type, BigDecimal amount, BigDecimal balanceAfterTransaction) {
}