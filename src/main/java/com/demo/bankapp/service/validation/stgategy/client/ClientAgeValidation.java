package com.demo.bankapp.service.validation.stgategy.client;

import com.demo.bankapp.exception.client.ClientUnderAgeException;
import com.demo.bankapp.model.entity.ClientEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.Predicate;

@Component
public class ClientAgeValidation implements ClientValidationStrategy {

    // TODO вынести в properties
//    @Value("${client.min_age}")
    private final int CLIENT_MIN_AGE = 18;

    final Predicate<LocalDate> isAdult = d -> d.plusYears(CLIENT_MIN_AGE).isAfter(LocalDate.now());

    @Override
    public void validate(final ClientEntity clientEntity) throws ClientUnderAgeException {
        if (isAdult.test(clientEntity.getBirthDate())) {
            throw new ClientUnderAgeException("The clientEntity has not reached the age of majority.");
        }
    }
}
