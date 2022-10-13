package com.demo.bankapp.controller;

import com.demo.bankapp.controller.request.ClientRequest;
import com.demo.bankapp.controller.response.Response;
import com.demo.bankapp.controller.response.ResponseWithBody;
import com.demo.bankapp.exception.client.ClientException;
import com.demo.bankapp.model.Client;
import com.demo.bankapp.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Api(value = "Client")
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @CrossOrigin
    @ApiOperation(value = "Add new client", response = Client.class)
    @RequestMapping(value = "/addNewClient", method = RequestMethod.POST)
    public ResponseWithBody<Client> addNewClient(
            @ApiParam(name = "client", required = true, value = "client")
            @RequestBody final ClientRequest clientRequest
    ) {
        final ResponseWithBody<Client> responseWithBody = new ResponseWithBody<>();
        try {
            responseWithBody
                    .setSuccess(true)
                    .setResponseBody(clientService.addNewClient(clientRequest));
        } catch (Exception e) {
            responseWithBody
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return responseWithBody;
    }

    @CrossOrigin
    @ApiOperation(value = "Get client by id", response = Client.class)
    @RequestMapping(value = "/getClientById", method = RequestMethod.GET)
    public ResponseWithBody<Client> getClientById(
            @ApiParam(name = "clientId", required = true, value = "clientId")
            @RequestParam("clientId") final long clientId
    ) {
        final ResponseWithBody<Client> responseWithBody = new ResponseWithBody<>();
        try {
            responseWithBody
                    .setSuccess(true)
                    .setResponseBody(clientService.getClientById(clientId));
        } catch (ClientException e) {
            responseWithBody
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return responseWithBody;
    }

    @ApiOperation(value = "Update client", response = Client.class)
    @RequestMapping(value = "/updateClient", method = RequestMethod.POST)
    public ResponseWithBody<Client> updateClient(
            @ApiParam(name = "client", required = true, value = "client")
            @RequestBody final Client client
    ) {
        final ResponseWithBody<Client> responseWithBody = new ResponseWithBody<>();
        try {
            responseWithBody
                    .setSuccess(true)
                    .setResponseBody(clientService.updateClient(client));
        } catch (ClientException e) {
            responseWithBody
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return responseWithBody;
    }

    @ApiOperation(value = "Delete client by id", response = Response.class)
    @RequestMapping(value = "/deleteClientById", method = RequestMethod.DELETE)
    public Response deleteClientById(
            @ApiParam(name = "clientId", required = true, value = "clientId")
            @RequestParam("clientId") final long clientId
    ) {
        final Response responseWithBody = new Response();
        try {
            clientService.deleteClient(clientId);
            responseWithBody.setSuccess(true);
        } catch (Exception e) {
            responseWithBody
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return responseWithBody;
    }
}
