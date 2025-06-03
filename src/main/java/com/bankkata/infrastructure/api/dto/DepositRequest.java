package com.bankkata.infrastructure.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * DTO for deposit requests.
 *
 * @param amount The amount to deposit
 */
public record DepositRequest(@NotNull(message = "Amount cannot be null")
                             @DecimalMin(value = "0.01", message = "Amount must be greater than zero") BigDecimal amount) {
}
