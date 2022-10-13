package com.demo.bankapp.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Primary
@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "clients")
public class ClientEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long clientId;

    private String name;
    private LocalDate birthDate;
    private String email;

    @OneToMany(mappedBy = "clientEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AccountEntity> accounts;

    @OneToMany(mappedBy = "clientEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<LoanEntity> loans;
}
