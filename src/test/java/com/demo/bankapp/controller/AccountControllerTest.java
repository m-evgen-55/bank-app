package com.demo.bankapp.controller;

import com.demo.bankapp.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static com.demo.bankapp.test_data.TestData.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class here just to show one more way of unit testing.
 */
class AccountControllerTest {

    private AccountService accountService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        accountService = mock(AccountService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void addNewAccount() throws Exception {
        when(accountService.addNewAccount(anyLong(), any())).thenReturn(getAccount());

        mockMvc.perform(post("/account/addNewAccount?clientId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getAccount())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.id").value("2"))
                .andExpect(jsonPath("$.responseBody.balance").value("1000"))
                .andExpect(jsonPath("$.responseBody.clientId").value("1"));
    }

    // TODO разобраться почему не работает "responseBody":null
    @Test
    void putMoney() throws Exception {
        when(accountService.putMoney(anyLong(), BigDecimal.valueOf(anyInt()))).thenReturn(getAccount());

        mockMvc.perform(get("/account/putMoneyIntoAccount?accountId=2&putSum=500"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"));
    }

    // TODO разобраться почему не работает
    @Test
    void getMoney() throws Exception {
        when(accountService.getMoney(anyLong(), BigDecimal.valueOf(anyInt()))).thenReturn(getAccount());

        mockMvc.perform(get("/account/getMoneyFromAccount?accountId=2&getSum=500"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"));
    }

    @Test
    void findAccountById() throws Exception {
        when(accountService.findAccountById(anyLong())).thenReturn(getAccount());

        mockMvc.perform(get("/account/findAccountById?accountId=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.id").value("2"))
                .andExpect(jsonPath("$.responseBody.balance").value("1000"))
                .andExpect(jsonPath("$.responseBody.clientId").value("1"));
    }

    @Test
    void getClientAccountsTest() throws Exception {
        when(accountService.getAllClientAccounts(anyLong())).thenReturn(getAccountList());

        mockMvc.perform(get("/account/getClientAccounts?clientId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.[0].id").value("2"))
                .andExpect(jsonPath("$.responseBody.[0].balance").value("1000"))
                .andExpect(jsonPath("$.responseBody.[0].clientId").value("1"))
                .andExpect(jsonPath("$.responseBody.[1].id").value("3"))
                .andExpect(jsonPath("$.responseBody.[1].balance").value("1200"))
                .andExpect(jsonPath("$.responseBody.[1].clientId").value("1"));
    }

    @Test
    void getClientByAccountId() throws Exception {
        when(accountService.getClientByAccountId(anyLong())).thenReturn(getClient());

        mockMvc.perform(get("/account/getClientByAccountId?accountId=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.id").value("1"))
                .andExpect(jsonPath("$.responseBody.name").value("Ivanov Ivan"));
        // TODO разобраться с birthDate
//                .andExpect(jsonPath("$.responseBody.birthDate").value("1980,4,25"));
    }

    @Test
    void deleteAccountById() throws Exception {
        mockMvc.perform(delete("/account/deleteAccountById?accountId=2"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.success").value("true"));
    }
}