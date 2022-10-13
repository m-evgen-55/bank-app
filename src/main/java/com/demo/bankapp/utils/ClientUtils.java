package com.demo.bankapp.utils;

import com.demo.bankapp.controller.request.ClientRequest;
import com.demo.bankapp.model.Client;
import com.demo.bankapp.model.entity.ClientEntity;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ClientUtils {

    private ClientUtils() {
    }

    public static Client mapClientEntityToClient(final ClientEntity clientEntity) {
        return new Client()
                .setId(clientEntity.getClientId())
                .setName(clientEntity.getName())
                .setBirthDate(clientEntity.getBirthDate())
                .setEmail(clientEntity.getEmail())
                .setAccounts(
                        Optional.ofNullable(clientEntity.getAccounts()).orElse(new ArrayList<>())
                                .stream()
                                .map(AccountUtils::mapAccountEntityToAccount)
                                .collect(Collectors.toList())
                )
                .setLoans(
                        Optional.ofNullable(clientEntity.getLoans()).orElse(new ArrayList<>())
                                .stream()
                                .map(LoanUtils::mapLoanEntityToLoan)
                                .collect(Collectors.toList())
                );
    }

    public static ClientEntity mapClientRequestToClientEntity(final ClientRequest clientRequest) {
        return new ClientEntity()
                .setName(clientRequest.getName())
                .setEmail(clientRequest.getEmail())
                .setBirthDate(clientRequest.getBirthDate());
    }
}
