package com.demo.bankapp.service;

import com.demo.bankapp.controller.request.ClientRequest;
import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.exception.loan.AgeExceededException;
import com.demo.bankapp.model.Client;

public interface ClientService {

    Client addNewClient(ClientRequest clientRequest) throws ClientException, AgeExceededException;

    Client getClientById(long clientId) throws ClientException;

    Client updateClient(Client client) throws ClientException;

    void deleteClient(long clientId) throws ClientException, AgeExceededException;
}

