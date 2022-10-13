package com.demo.bankapp.repository;

import com.demo.bankapp.model.entity.ClientEntity;
import com.demo.bankapp.model.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    List<LoanEntity> findLoanEntitiesByClientEntity(ClientEntity clientEntity);
}
