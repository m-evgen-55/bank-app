package com.demo.bankapp.service;

import com.demo.bankapp.controller.request.ClientRequest;
import com.demo.bankapp.dao.ClientDao;
import com.demo.bankapp.exception.client.ClientDebtorException;
import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.exception.client.ClientUnderAgeException;
import com.demo.bankapp.exception.loan.AgeExceededException;
import com.demo.bankapp.model.Client;
import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.service.validation.ClientValidationServiceImpl;
import com.demo.bankapp.service.validation.stgategy.client.ClientAgeValidation;
import com.demo.bankapp.service.validation.stgategy.client.ClientHasLoansValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static com.demo.bankapp.test_data.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    private ClientDao clientDao;
    private ClientService clientService;
    private LoanService loanService;

    @BeforeEach
    public void setUp() {
        clientDao = mock(ClientDao.class);
        loanService = mock(LoanService.class);
        clientService = new ClientServiceImpl(
                clientDao,
                new ClientValidationServiceImpl(),
                new ClientAgeValidation(),
                new ClientHasLoansValidation(loanService));
    }

    @Test
    void addNewClientPositiveTest() throws AgeExceededException, ClientException {
        final ClientEntity clientEntity = getClientEntity();
        when(clientDao.insertClient(any())).thenReturn(clientEntity);

        final Client client = clientService.addNewClient(getClientRequest());

        assertThat(client).isNotNull();
        Assertions.assertEquals(clientEntity.getClientId(), client.getId());
        Assertions.assertEquals(clientEntity.getName(), client.getName());
        Assertions.assertEquals(clientEntity.getBirthDate(), client.getBirthDate());
        Assertions.assertEquals(clientEntity.getEmail(), client.getEmail());
    }

    @Test
    void addNewClientNegativeTest() {
        final ClientEntity clientEntity = getClientEntity();
        when(clientDao.insertClient(any())).thenReturn(clientEntity);

        final ClientRequest clientRequest = getClientRequest();
        clientRequest.setBirthDate(LocalDate.of(2010, 4, 25));

        Assertions.assertThrows(ClientUnderAgeException.class, () -> clientService.addNewClient(clientRequest));
    }

    @Test
    void getClientByIdTest() throws ClientException {
        final ClientEntity clientEntity = getClientEntity();
        when(clientDao.getClientById(anyLong())).thenReturn(Optional.of(clientEntity));

        final Client client = clientService.getClientById(1);

        assertThat(client).isNotNull();
        Assertions.assertEquals(clientEntity.getClientId(), client.getId());
        Assertions.assertEquals(clientEntity.getName(), client.getName());
        Assertions.assertEquals(clientEntity.getBirthDate(), client.getBirthDate());
        Assertions.assertEquals(clientEntity.getEmail(), client.getEmail());
    }

    @Test
    void updateClientTest() throws ClientException {
        final ClientEntity clientEntity = getClientEntity();
        when(clientDao.getClientById(anyLong())).thenReturn(Optional.of(clientEntity));
        when(clientDao.insertClient(any())).thenReturn(clientEntity);

        final Client client = clientService.updateClient(getClient());
        assertThat(client).isNotNull();
        Assertions.assertEquals(clientEntity.getClientId(), client.getId());
        Assertions.assertEquals(clientEntity.getName(), client.getName());
        Assertions.assertEquals(clientEntity.getBirthDate(), client.getBirthDate());
        Assertions.assertEquals(clientEntity.getEmail(), client.getEmail());
    }

    @Test
    void deleteClientTest() throws AgeExceededException, ClientException {
        when(clientDao.getClientById(anyLong())).thenReturn(Optional.of(getClientEntity()));

        clientService.deleteClient(1);

        verify(clientDao, times(1)).deleteClient(1L);
    }

    @Test
    void deleteClientWithLoanNegativeTest() throws ClientException {
        when(clientDao.getClientById(anyLong())).thenReturn(Optional.of(getClientEntity()));
        when(loanService.getClientLoans(anyLong())).thenReturn(getLoanList());

        Assertions.assertThrows(ClientDebtorException.class, () -> clientService.deleteClient(1));
    }
}