package com.bankkata.bankaccount.infrastructure.api;

import com.bankkata.bankaccount.application.service.AccountService;
import com.bankkata.bankaccount.domain.model.Transaction;
import com.bankkata.bankaccount.infrastructure.api.dto.DepositRequest;
import com.bankkata.bankaccount.infrastructure.api.dto.TransactionResponse;
import com.bankkata.bankaccount.infrastructure.api.dto.WithdrawalRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@Valid @RequestBody DepositRequest request) {
        try {
            accountService.deposit(request.amount());
            return ResponseEntity.ok("Deposit successful. New balance: " + accountService.getBalance());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<String> withdraw(@Valid @RequestBody WithdrawalRequest request) {
        try {
            accountService.withdraw(request.amount());
            return ResponseEntity.ok("Withdrawal successful. New balance: " + accountService.getBalance());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance() {
        return ResponseEntity.ok(accountService.getBalance());
    }

    @GetMapping("/statement")
    public ResponseEntity<List<TransactionResponse>> getStatement() {
        List<Transaction> domainTransactions = accountService.getStatementTransactions();
        List<TransactionResponse> response = domainTransactions.stream()
                .map(TransactionResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}