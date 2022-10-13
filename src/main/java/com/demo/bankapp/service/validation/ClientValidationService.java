package com.demo.bankapp.service.validation;

import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.exception.loan.AgeExceededException;
import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.service.validation.stgategy.client.ClientValidationStrategy;

import java.util.List;

public interface ClientValidationService {

    void validate(List<ClientValidationStrategy> clientValidationStrategyList, ClientEntity clientEntity) throws ClientException, AgeExceededException;
}
