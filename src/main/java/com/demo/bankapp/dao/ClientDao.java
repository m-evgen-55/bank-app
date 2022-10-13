package com.demo.bankapp.dao;

import com.demo.bankapp.model.entity.ClientEntity;

import java.util.Optional;

public interface ClientDao {

    ClientEntity insertClient(ClientEntity clientEntity);

    Optional<ClientEntity> getClientById(long clientId);

    void deleteClient(long clientId);
}
