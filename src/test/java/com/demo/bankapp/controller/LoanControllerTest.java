package com.demo.bankapp.controller;

import com.demo.bankapp.service.LoanService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class here just to show one more way of unit testing.
 */
class LoanControllerTest {

    private LoanService loanService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        loanService = mock(LoanService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new LoanController(loanService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void addNewLoanTest() throws Exception {
        when(loanService.addNewLoan(any())).thenReturn(getLoan());

        mockMvc.perform(post("/loan/addNewLoan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getLoanRequest())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.id").value("5"))
                .andExpect(jsonPath("$.responseBody.sum").value("2000"))
                .andExpect(jsonPath("$.responseBody.rate").value("9"))
                .andExpect(jsonPath("$.responseBody.overpayment").value("360"))
                .andExpect(jsonPath("$.responseBody.duration").value("24"))
                .andExpect(jsonPath("$.responseBody.toBeReturnedSum").value("2360"))
                .andExpect(jsonPath("$.responseBody.alreadyReturnedSum").value("2360"))
                .andExpect(jsonPath("$.responseBody.clientId").value("1"))
                .andExpect(jsonPath("$.responseBody.paidOff").value("true"));
    }

    // TODO разобраться почему не работает "responseBody":null
    @Test
    void payForLoanTest() throws Exception {
        when(loanService.payForLoan(anyLong(), BigDecimal.valueOf(anyInt()))).thenReturn(getLoan());

        mockMvc.perform(get("/loan/payForLoan?loanId=5&paySum=500"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"));
    }

    @Test
    void getLoanByIdTest() throws Exception {
        when(loanService.getLoanById(anyLong())).thenReturn(getLoan());

        mockMvc.perform(get("/loan/getLoanById?loanId=5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.id").value("5"))
                .andExpect(jsonPath("$.responseBody.sum").value("2000"))
                .andExpect(jsonPath("$.responseBody.rate").value("9"))
                .andExpect(jsonPath("$.responseBody.overpayment").value("360"))
                .andExpect(jsonPath("$.responseBody.duration").value("24"))
                .andExpect(jsonPath("$.responseBody.toBeReturnedSum").value("2360"))
                .andExpect(jsonPath("$.responseBody.alreadyReturnedSum").value("2360"))
                .andExpect(jsonPath("$.responseBody.clientId").value("1"))
                .andExpect(jsonPath("$.responseBody.paidOff").value("true"));
    }

    @Test
    void getClientLoansTest() throws Exception {
        when(loanService.getClientLoans(anyLong())).thenReturn(getLoanList());

        mockMvc.perform(get("/loan/getClientLoans?clientId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.[0].id").value("4"))
                .andExpect(jsonPath("$.responseBody.[0].sum").value("1500"))
                .andExpect(jsonPath("$.responseBody.[0].rate").value("10"))
                .andExpect(jsonPath("$.responseBody.[0].overpayment").value("150"))
                .andExpect(jsonPath("$.responseBody.[0].duration").value("12"))
                .andExpect(jsonPath("$.responseBody.[0].toBeReturnedSum").value("1650"))
                .andExpect(jsonPath("$.responseBody.[0].alreadyReturnedSum").value("500"))
                .andExpect(jsonPath("$.responseBody.[0].clientId").value("1"))
                .andExpect(jsonPath("$.responseBody.[0].paidOff").value("false"))

                .andExpect(jsonPath("$.responseBody.[1].id").value("5"))
                .andExpect(jsonPath("$.responseBody.[1].sum").value("2000"))
                .andExpect(jsonPath("$.responseBody.[1].rate").value("9"))
                .andExpect(jsonPath("$.responseBody.[1].overpayment").value("360"))
                .andExpect(jsonPath("$.responseBody.[1].duration").value("24"))
                .andExpect(jsonPath("$.responseBody.[1].toBeReturnedSum").value("2360"))
                .andExpect(jsonPath("$.responseBody.[1].alreadyReturnedSum").value("2360"))
                .andExpect(jsonPath("$.responseBody.[1].clientId").value("1"))
                .andExpect(jsonPath("$.responseBody.[1].paidOff").value("true"));
    }

    @Test
    void getClientByLoanIdTest() throws Exception {
        when(loanService.getClientByLoanId(anyLong())).thenReturn(getClient());

        mockMvc.perform(get("/loan/getClientByLoanId?loanId=5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.id").value("1"))
                .andExpect(jsonPath("$.responseBody.name").value("Ivanov Ivan"));
        // TODO разобраться почему не работает
//                .andExpect(jsonPath("$.responseBody.birthDate").value("[1980,4,25]"));
    }
}