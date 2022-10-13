package com.demo.bankapp.dao;

import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientDaoImpl implements ClientDao {

    private final ClientRepository clientRepository;

    @Override
    public ClientEntity insertClient(final ClientEntity clientEntity) {
        return clientRepository.save(clientEntity);
    }

    @Override
    public Optional<ClientEntity> getClientById(final long clientId) {
        return clientRepository.findById(clientId);
    }

    @Override
    public void deleteClient(final long clientId) {
        clientRepository.deleteById(clientId);
    }
}
