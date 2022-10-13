package com.demo.bankapp.repository;

import com.demo.bankapp.model.entity.AccountEntity;
import com.demo.bankapp.model.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    List<AccountEntity> findAccountEntitiesByClientEntity(ClientEntity clientEntity);
}
