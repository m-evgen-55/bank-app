package com.demo.bankapp.dao;

import com.demo.bankapp.model.entity.LoanEntity;

import java.util.Optional;

public interface LoanDao {

    LoanEntity insertLoan(LoanEntity loanEntity);

    Optional<LoanEntity> findLoanById(long loanId);
}
