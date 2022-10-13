package com.demo.bankapp.dao;

import com.demo.bankapp.model.entity.LoanEntity;
import com.demo.bankapp.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoanDaoImpl implements LoanDao {

    private final LoanRepository loanRepository;

    @Override
    public LoanEntity insertLoan(final LoanEntity loanEntity) {
        return loanRepository.save(loanEntity);
    }

    @Override
    public Optional<LoanEntity> findLoanById(final long loanId) {
        return loanRepository.findById(loanId);
    }
}
