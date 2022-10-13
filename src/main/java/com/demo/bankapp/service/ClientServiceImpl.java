package com.demo.bankapp.service;

import com.demo.bankapp.controller.request.ClientRequest;
import com.demo.bankapp.dao.ClientDao;
import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.exception.client.ClientNotFoundException;
import com.demo.bankapp.exception.loan.AgeExceededException;
import com.demo.bankapp.model.Client;
import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.service.validation.ClientValidationService;
import com.demo.bankapp.service.validation.stgategy.client.ClientAgeValidation;
import com.demo.bankapp.service.validation.stgategy.client.ClientHasLoansValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.demo.bankapp.utils.ClientUtils.mapClientEntityToClient;
import static com.demo.bankapp.utils.ClientUtils.mapClientRequestToClientEntity;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;
    private final ClientValidationService clientValidationService;
    private final ClientAgeValidation clientAgeValidation;
    private final ClientHasLoansValidation clientHasLoansValidation;

    @Override
    public Client addNewClient(final ClientRequest clientRequest) throws ClientException, AgeExceededException {
        final ClientEntity clientEntity = mapClientRequestToClientEntity(clientRequest);
        clientValidationService.validate(Collections.singletonList(clientAgeValidation), clientEntity);
        final ClientEntity savedClient = clientDao.insertClient(clientEntity);
        return mapClientEntityToClient(savedClient);
    }

    @Override
    public Client getClientById(final long clientId) throws ClientNotFoundException {
        final ClientEntity clientEntity = clientDao.getClientById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client id = %s not found", clientId)));

        return mapClientEntityToClient(clientEntity);
    }

    @Override
    public Client updateClient(Client client) throws ClientException {
        final ClientEntity clientEntity = clientDao.getClientById(client.getId())
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client id = %s not found", client.getId())));

        clientEntity.setName(client.getName());
        clientEntity.setBirthDate(client.getBirthDate());

        return mapClientEntityToClient(clientDao.insertClient(clientEntity));
    }

    // the method checks that the client has no outstanding loans
    // you can't delete a customer with outstanding loans
    // when deleting a client, all associated accounts and loans are also deleted
    @Override
    public void deleteClient(final long clientId) throws ClientException, AgeExceededException {
        final ClientEntity clientEntity = clientDao.getClientById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client id = %s not found", clientId)));

        clientValidationService.validate(Collections
                .singletonList(clientHasLoansValidation), clientEntity);
        clientDao.deleteClient(clientId);
    }
}
