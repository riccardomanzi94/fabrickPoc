package it.poc.fabrick.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import it.poc.fabrick.Config.YamlConfig;
import it.poc.fabrick.model.dto.MoneyTransferDto;
import it.poc.fabrick.model.dto.ResponseTransactionDto;
import it.poc.fabrick.model.dto.TransactionDto;
import it.poc.fabrick.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
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


    @GetMapping(value = "get/accounts/{accountId}/balance")
    @ApiOperation(
            value = "Lettura saldo",
            authorizations = @Authorization(value = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful"),
    })
    public String getBalance(@PathVariable String accountId) throws JsonProcessingException {

        log.info("FabrickController - GetBalance - START");

        ResponseEntity<String> response = restTemplate.exchange(
                yamlConfig.getBaseUrl() + accountId + "/balance",
                HttpMethod.GET,
                createHeaders(),
                String.class);

        log.info("FabrickController - GetBalance - END");

        return response.getBody();
    }


    @PostMapping(value = "post/accounts/{accountId}/payment/money-transfers")
    @ApiOperation(
            value = "Bonifico",
            authorizations = @Authorization(value = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful"),
    })
    public ResponseEntity<String> createMoneyTransfer(@PathVariable String accountId, @RequestBody @Valid MoneyTransferDto moneyTransferDto) throws JsonProcessingException {

        ResponseEntity<String> responseEntity;
        HttpEntity<MoneyTransferDto> requestEntity = new HttpEntity<>(moneyTransferDto, createHeaders().getHeaders());

        try{
            log.info("FabrickController - createMoneyTransfer - START");
            responseEntity = restTemplate.postForEntity(yamlConfig.getBaseUrl() + accountId + "/payments/money-transfers"
                    , requestEntity, String.class);
        }catch(HttpClientErrorException ex){
            log.debug("Fabrick Controller - createMoneyTransfer - Exception: " + ex.getMessage());
            return new ResponseEntity<>(ex.getResponseBodyAsString(),HttpStatus.BAD_REQUEST);
        }

        log.info("FabrickController - createMoneyTransfer - END");
        return responseEntity;

    }

    @GetMapping(value = "get/accounts/{accountId}/transactions")
    @ApiOperation(
            value = "Lettura transazioni",
            authorizations = @Authorization(value = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful"),
    })
    public ResponseEntity<?> getTransactions(@PathVariable String accountId, @RequestParam LocalDate fromAccountingDate, @RequestParam LocalDate toAccountingDate) throws JsonProcessingException {

        log.info("FabrickController - GetTransactions - START");

        ResponseEntity<String> response = restTemplate.exchange(
                yamlConfig.getBaseUrl() + accountId + "/transactions?fromAccountingDate="
                        + fromAccountingDate + "&toAccountingDate=" + toAccountingDate,
                HttpMethod.GET,
                createHeaders(),
                String.class);

        List<TransactionDto> responseDto = objectMapper.readValue(response.getBody(),ResponseTransactionDto.class).getPayload().getList();

        transactionService.saveTransactions(responseDto);

        log.info("FabrickController - GetTransactions - END");

        return new ResponseEntity<>(response.getBody(),response.getStatusCode());
    }

    //Funzione utilizzata per la creazione dei vari headers alle API Rest Fabrick
    private HttpEntity<String> createHeaders(){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Auth-Schema", yamlConfig.getAuthSchema());
        httpHeaders.set("Api-Key", yamlConfig.getApiKey());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(httpHeaders);
    }

}
