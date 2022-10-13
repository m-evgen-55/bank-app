package com.demo.bankapp.service.validation;

import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.exception.loan.AgeExceededException;
import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.service.validation.stgategy.client.ClientValidationStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientValidationServiceImpl implements ClientValidationService {

    @Override
    public void validate(
            List<ClientValidationStrategy> clientValidationStrategyList,
            final ClientEntity clientEntity
    ) throws ClientException, AgeExceededException {
        for (ClientValidationStrategy clientValidationStrategy : clientValidationStrategyList) {
            clientValidationStrategy.validate(clientEntity);
        }
    }
}
