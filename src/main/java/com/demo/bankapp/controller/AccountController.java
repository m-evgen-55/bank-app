package com.demo.bankapp.controller;

import com.demo.bankapp.controller.response.Response;
import com.demo.bankapp.controller.response.ResponseWithBody;
import com.demo.bankapp.exception.account.AccountException;
import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.model.Account;
import com.demo.bankapp.model.Client;
import com.demo.bankapp.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Api(value = "Account")
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @ApiOperation(value = "Add new account for client", response = Account.class)
    @RequestMapping(value = "/addNewAccount", method = RequestMethod.POST)
    public ResponseWithBody<Account> addNewAccount(
            @ApiParam(name = "clientId", required = true, value = "clientId")
            @RequestParam("clientId") final long clientId,
            @ApiParam(name = "account", required = true, value = "account")
            @RequestBody final Account account
    ) {
        final ResponseWithBody<Account> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(accountService.addNewAccount(clientId, account));
        } catch (ClientException e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Put money into account", response = Account.class)
    @RequestMapping(value = "/putMoneyIntoAccount", method = RequestMethod.GET)
    public ResponseWithBody<Account> putMoney(
            @ApiParam(name = "accountId", required = true, value = "accountId")
            @RequestParam("accountId") final long accountId,
            @ApiParam(name = "putSum", required = true, value = "putSum")
            @RequestParam("putSum") final BigDecimal putSum
    ) {
        final ResponseWithBody<Account> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(accountService.putMoney(accountId, putSum));
        } catch (AccountException e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Get money from account", response = Account.class)
    @RequestMapping(value = "/getMoneyFromAccount", method = RequestMethod.GET)
    public ResponseWithBody<Account> getMoney(
            @ApiParam(name = "accountId", required = true, value = "accountId")
            @RequestParam("accountId") final long accountId,
            @ApiParam(name = "getSum", required = true, value = "getSum")
            @RequestParam("getSum") final BigDecimal getSum
    ) {
        final ResponseWithBody<Account> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(accountService.getMoney(accountId, getSum));
        } catch (AccountException e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Find account by id", response = Account.class)
    @RequestMapping(value = "/findAccountById", method = RequestMethod.GET)
    public ResponseWithBody<Account> findAccountById(
            @ApiParam(name = "accountId", required = true, value = "accountId")
            @RequestParam("accountId") final long accountId
    ) {
        final ResponseWithBody<Account> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(accountService.findAccountById(accountId));
        } catch (AccountException e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Get client accounts", response = Account.class, responseContainer = "List")
    @RequestMapping(value = "/getClientAccounts", method = RequestMethod.GET)
    public ResponseWithBody<List<Account>> getClientAccounts(
            @ApiParam(name = "clientId", required = true, value = "clientId")
            @RequestParam("clientId") final long clientId
    ) {
        final ResponseWithBody<List<Account>> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(accountService.getAllClientAccounts(clientId));
        } catch (ClientException e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Get client by account id", response = Client.class)
    @RequestMapping(value = "/getClientByAccountId", method = RequestMethod.GET)
    public ResponseWithBody<Client> getClientByAccountId(
            @ApiParam(name = "accountId", required = true, value = "accountId")
            @RequestParam("accountId") final long accountId
    ) {
        final ResponseWithBody<Client> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(accountService.getClientByAccountId(accountId));
        } catch (Exception e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Delete account", response = Response.class)
    @RequestMapping(value = "/deleteAccountById", method = RequestMethod.DELETE)
    public Response deleteAccountById(
            @ApiParam(name = "accountId", required = true, value = "accountId")
            @RequestParam("accountId") final long accountId
    ) {
        final Response response = new Response();
        try {
            accountService.deleteAccount(accountId);
            response.setSuccess(true);
        } catch (AccountException e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }
}
