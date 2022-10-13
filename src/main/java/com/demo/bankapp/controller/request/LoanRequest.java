package com.demo.bankapp.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class LoanRequest {

    private long clientId;
    private BigDecimal loanSum;
    private int loanTimeInMonth;
    private BigDecimal monthSalary;
}
