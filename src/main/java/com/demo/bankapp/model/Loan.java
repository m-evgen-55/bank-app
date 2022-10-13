package com.demo.bankapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class Loan {

    private long id;
    private BigDecimal sum;
    private int rate;
    private BigDecimal overpayment;
    private int duration;
    private BigDecimal toBeReturnedSum;
    private BigDecimal alreadyReturnedSum;
    private long clientId;
    private boolean isPaidOff;
}
