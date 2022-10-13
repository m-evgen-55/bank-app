package com.demo.bankapp.service.validation.stgategy.client;

import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.exception.loan.AgeExceededException;
import com.demo.bankapp.model.entity.ClientEntity;

public interface ClientValidationStrategy {

    void validate(ClientEntity clientEntity) throws ClientException, AgeExceededException;
}
