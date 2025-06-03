package com.bankkata.infrastructure.api;

import com.bankkata.application.service.AccountService;
import com.bankkata.infrastructure.api.dto.DepositRequest;
import com.bankkata.infrastructure.api.dto.WithdrawalRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return null;
    }
}