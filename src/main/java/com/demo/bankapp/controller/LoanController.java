package com.demo.bankapp.controller;

import com.demo.bankapp.controller.request.LoanRequest;
import com.demo.bankapp.controller.response.ResponseWithBody;
import com.demo.bankapp.exception.loan.LoanException;
import com.demo.bankapp.model.Client;
import com.demo.bankapp.model.Loan;
import com.demo.bankapp.service.LoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Api(value = "Loan")
@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @ApiOperation(value = "Add new loan", response = Loan.class)
    @RequestMapping(value = "/addNewLoan", method = RequestMethod.POST)
    public ResponseWithBody<Loan> addNewLoan(
            @ApiParam(name = "loanRequest", required = true, value = "loanRequest")
            @RequestBody final LoanRequest loanRequest
    ) {
        final ResponseWithBody<Loan> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(loanService.addNewLoan(loanRequest));
        } catch (Exception e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Pay for loan", response = Loan.class)
    @RequestMapping(value = "/payForLoan", method = RequestMethod.GET)
    public ResponseWithBody<Loan> payForLoan(
            @ApiParam(name = "loanId", required = true, value = "loanId")
            @RequestParam("loanId") final long loanId,
            @ApiParam(name = "paySum", required = true, value = "paySum")
            @RequestParam("paySum") final BigDecimal paySum
    ) {
        final ResponseWithBody<Loan> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(loanService.payForLoan(loanId, paySum));
        } catch (LoanException e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Get loan by id", response = Loan.class)
    @RequestMapping(value = "/getLoanById", method = RequestMethod.GET)
    public ResponseWithBody<Loan> getLoanById(
            @ApiParam(name = "loanId", required = true, value = "loanId")
            @RequestParam final long loanId
    ) {
        final ResponseWithBody<Loan> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(loanService.getLoanById(loanId));
        } catch (Exception e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Get client loans", response = Loan.class, responseContainer = "List")
    @RequestMapping(value = "/getClientLoans", method = RequestMethod.GET)
    public ResponseWithBody<List<Loan>> getClientLoans(
            @ApiParam(name = "clientId", required = true, value = "clientId")
            @RequestParam("clientId") final long clientId
    ) {
        final ResponseWithBody<List<Loan>> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(loanService.getClientLoans(clientId));
        } catch (Exception e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Get client by loan id", response = Client.class)
    @RequestMapping(value = "/getClientByLoanId", method = RequestMethod.GET)
    public ResponseWithBody<Client> getClientByLoanId(
            @ApiParam(name = "clientId", required = true, value = "clientId")
            @RequestParam("loanId") final long loanId
    ) {
        final ResponseWithBody<Client> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(loanService.getClientByLoanId(loanId));
        } catch (Exception e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }
}
