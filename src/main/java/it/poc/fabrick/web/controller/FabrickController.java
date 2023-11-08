package it.poc.fabrick.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import it.poc.fabrick.config.YamlConfig;
import it.poc.fabrick.model.dto.MoneyTransferDto;
import it.poc.fabrick.model.dto.TransactionDto;
import it.poc.fabrick.service.FabrickService;
import it.poc.fabrick.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@RestController
public class FabrickController {

    @Autowired
    private YamlConfig yamlConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FabrickService fabrickService;


    @GetMapping(value = "get/accounts/{accountId}/balance")
    @ApiOperation(
            value = "Lettura saldo",
            authorizations = @Authorization(value = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful"),
    })
    public ResponseEntity<?> getBalance(@PathVariable String accountId) throws JsonProcessingException {
        return fabrickService.getBalanceService(accountId);
    }

    @PostMapping(value = "post/accounts/{accountId}/payment/money-transfers")
    @ApiOperation(
            value = "Bonifico",
            authorizations = @Authorization(value = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful"),
    })
    public ResponseEntity<?> createMoneyTransfer(@PathVariable String accountId, @RequestBody @Valid MoneyTransferDto moneyTransferDto) throws JsonProcessingException {

       return fabrickService.createMoneyTransferService(accountId,moneyTransferDto);
    }

    @GetMapping(value = "get/accounts/{accountId}/transactions")
    @ApiOperation(
            value = "Lettura transazioni",
            authorizations = @Authorization(value = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful"),
    })
    public ResponseEntity<?> getTransactions(@PathVariable String accountId, @RequestParam LocalDate fromAccountingDate, @RequestParam LocalDate toAccountingDate) throws JsonProcessingException {

        return fabrickService.getTransactionsService(accountId,fromAccountingDate,toAccountingDate);
    }

    @GetMapping(value = "get/transactions")
    public List<TransactionDto> getAllTransactionFromDB(){
        return fabrickService.getAllTransactionsFromDB();
    }
}
