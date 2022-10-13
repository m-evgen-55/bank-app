package com.demo.bankapp.service.validation.stgategy.client;

import com.demo.bankapp.exception.loan.AgeExceededException;
import com.demo.bankapp.model.entity.ClientEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.Predicate;

@Component
public class LoanClientAgeClientValidation implements ClientValidationStrategy {

    // TODO вынести в properties
    private final int MAX_CLIENT_AGE = 50;

    final Predicate<LocalDate> isOlder = d -> d.plusYears(MAX_CLIENT_AGE).isBefore(LocalDate.now());

    @Override
    public void validate(ClientEntity clientEntity) throws AgeExceededException {
        if (isOlder.test(clientEntity.getBirthDate())) {
            throw new AgeExceededException("The clientEntity is not approved for a loan. The clientEntity has exceeded the maximum age.");
        }
    }
}
