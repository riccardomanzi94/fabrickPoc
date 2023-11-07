package it.poc.fabrick.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import it.poc.fabrick.Config.YamlConfig;
import it.poc.fabrick.dto.MoneyTransferDto;
import lombok.extern.slf4j.Slf4j;
import org.javalite.http.Get;
import org.javalite.http.Http;
import org.javalite.http.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Slf4j
@RestController
public class FabrickController {

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public YamlConfig yamlConfig;


    @GetMapping(value = "get/accounts/{accountId}/balance")
    @ApiOperation(
            value = "Lettura saldo",
            authorizations = @Authorization(value = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful"),
    })
    public String getBalance(@PathVariable String accountId) throws JsonProcessingException {

        log.info("FabrickController - GetBalance - START");

        Get get = Http.get(yamlConfig.getBaseUrl() + accountId + "/balance")
                .header("Auth-Schema", yamlConfig.getAuthSchema())
                .header("Api-Key", yamlConfig.getApiKey());

        log.info("FabrickController - GetBalance - END");

        return get.text();
    }


    @PostMapping(value = "post/accounts/{accountId}/payment/money-transfers")
    @ApiOperation(
            value = "Bonifico",
            authorizations = @Authorization(value = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful"),
    })
    public String createMoneyTransfer(@PathVariable String accountId, @RequestBody MoneyTransferDto moneyTransferDto) throws JsonProcessingException {

        byte[] content = objectMapper.writeValueAsBytes(moneyTransferDto);
        String content2 = objectMapper.writeValueAsString(moneyTransferDto);

        log.debug(moneyTransferDto.toString());

        Post post = Http.post(yamlConfig.getBaseUrl() + accountId + "/payments/money-transfers", content2)
                .header("Auth-Schema", yamlConfig.getAuthSchema())
                .header("Api-Key", yamlConfig.getApiKey())
                .header("X-Time-Zone", yamlConfig.getTimeZone())
                .header("Content-Type","application/json");

        return post.text();
    }

    @GetMapping(value = "get/accounts/{accountId}/transactions")
    @ApiOperation(
            value = "Lettura transazioni",
            authorizations = @Authorization(value = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful"),
    })
    public String getTransactions(@PathVariable String accountId, @RequestParam LocalDate fromAccountingDate, @RequestParam LocalDate toAccountingDate){

        log.info("FabrickController - GetTransactions - START");

        Get get = Http.get(yamlConfig.getBaseUrl() + accountId + "/transactions?fromAccountingDate="
                        + fromAccountingDate + "&toAccountingDate=" + toAccountingDate)
                .header("Auth-Schema", yamlConfig.getAuthSchema())
                .header("Api-Key", yamlConfig.getApiKey());

        log.info("FabrickController - GetTransactions - END");

        return get.text().toString();

    }

}
