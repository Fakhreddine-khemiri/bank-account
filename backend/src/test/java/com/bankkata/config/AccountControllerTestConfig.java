package com.bankkata.config;

import com.bankkata.bankaccount.application.service.AccountService;
import com.bankkata.bankaccount.application.service.ClockService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class AccountControllerTestConfig {
    @Bean
    public AccountService accountService() {
        return mock(AccountService.class);
    }

    @Bean
    public ClockService clockService() {
        return mock(ClockService.class);
    }
}
