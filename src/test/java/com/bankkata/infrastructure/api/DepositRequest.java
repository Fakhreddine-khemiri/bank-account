package com.bankkata.infrastructure.api;

import java.math.BigDecimal;

/**
 * DTO for deposit requests.
 *
 * @param amount The amount to deposit
 */
public record DepositRequest(BigDecimal amount) {
}
