package com.demo.bankapp.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "loans")
public class LoanEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private BigDecimal sum;

    private int rate;

    private BigDecimal overpayment;

    private int duration;

    private BigDecimal toBeReturnedSum;

    private BigDecimal alreadyReturnedSum;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private ClientEntity clientEntity;

    private boolean isPaidOff;
}
