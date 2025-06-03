package com.bankkata.infrastructure.api;

import com.bankkata.application.service.AccountService;
import com.bankkata.config.AccountControllerTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AccountController.class)
@AutoConfigureMockMvc
@AutoConfigureJson
@Import(AccountControllerTestConfig.class)
@DisplayName("Account Controller Integration Tests")
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private AccountService accountService;


    @Test
    @DisplayName("POST /deposit - Valid request should return OK and success message")
    void givenValidDepositRequest_whenPostDeposit_thenReturnsOk() throws Exception {
        DepositRequest depositRequest = new DepositRequest(BigDecimal.valueOf(100.00));
        BigDecimal expectedBalanceAfterDeposit = BigDecimal.valueOf(100.00);

        doNothing().when(accountService).deposit(depositRequest.amount());
        when(accountService.getBalance()).thenReturn(expectedBalanceAfterDeposit);

        mockMvc.perform(post("/api/account/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Deposit successful. New balance: " + expectedBalanceAfterDeposit));

        verify(accountService).deposit(depositRequest.amount());
        verify(accountService).getBalance();
    }

    @Test
    @DisplayName("POST /deposit - Invalid request (negative amount) should return Bad Request")
    void givenInvalidDepositRequest_whenPostDeposit_thenStatusBadRequest() throws Exception {
        DepositRequest invalidRequest = new DepositRequest(BigDecimal.valueOf(-10.0));
        mockMvc.perform(post("/api/account/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
        verify(accountService, never()).deposit(any(BigDecimal.class));
    }
}