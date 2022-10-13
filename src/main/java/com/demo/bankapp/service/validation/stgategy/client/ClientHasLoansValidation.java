package com.demo.bankapp.service.validation.stgategy.client;

import com.demo.bankapp.exception.client.ClientDebtorException;
import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.model.Loan;
import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientHasLoansValidation implements ClientValidationStrategy {

    private final LoanService loanService;

    @Override
    public void validate(final ClientEntity clientEntity) throws ClientException {
        final List<Loan> clientLoans = loanService.getClientLoans(clientEntity.getClientId());

        if (clientLoans.stream().anyMatch(loan -> !loan.isPaidOff())) {
            throw new ClientDebtorException(String
                    .format("The clientEntity id = %s cannot be deleted. The clientEntity has outstanding loans.", clientEntity.getClientId()));
        }
    }
}
