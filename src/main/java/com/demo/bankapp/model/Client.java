package com.demo.bankapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Client {

    private long id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private List<Account> accounts;
    private List<Loan> loans;
}
