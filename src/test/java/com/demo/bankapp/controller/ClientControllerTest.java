package com.demo.bankapp.controller;

import com.demo.bankapp.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.demo.bankapp.test_data.TestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class here just to show one more way of unit testing.
 */
class ClientControllerTest {

    private ClientService clientService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        clientService = mock(ClientService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ClientController(clientService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void addNewClientTest() throws Exception {
        when(clientService.addNewClient(any())).thenReturn(getClient());

        mockMvc.perform(post("/client/addNewClient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getClientRequest())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.id").value("1"))
                .andExpect(jsonPath("$.responseBody.name").value("Ivanov Ivan"))
                .andExpect(jsonPath("$.responseBody.email").value("ivanov@yandex.ru"));
        // TODO разобраться с LocalDate
//                .andExpect(jsonPath("$.responseBody.birthDate").value("[1980,4,25]"));
    }

    @Test
    void getClientByIdTest() throws Exception {
        when(clientService.getClientById(anyLong())).thenReturn(getClient());

        mockMvc.perform(get("/client/getClientById?clientId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.id").value("1"))
                .andExpect(jsonPath("$.responseBody.name").value("Ivanov Ivan"))
                .andExpect(jsonPath("$.responseBody.email").value("ivanov@yandex.ru"));
        // TODO разобраться с LocalDate
//                .andExpect(jsonPath("$.responseBody.birthDate").value("[1980,4,25]"));
    }

    @Test
    void updateClientTest() throws Exception {
        when(clientService.updateClient(any())).thenReturn(getClient());

        mockMvc.perform(post("/client/updateClient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getClientRequest())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.id").value("1"))
                .andExpect(jsonPath("$.responseBody.name").value("Ivanov Ivan"))
                .andExpect(jsonPath("$.responseBody.email").value("ivanov@yandex.ru"));
    }

    @Test
    void deleteClientByIdTest() throws Exception {
        mockMvc.perform(delete("/client/deleteClientById?clientId=1"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.success").value("true"));
    }
}