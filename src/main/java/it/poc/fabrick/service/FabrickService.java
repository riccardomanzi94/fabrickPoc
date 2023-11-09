package it.poc.fabrick.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.poc.fabrick.config.YamlConfig;
import it.poc.fabrick.mapper.TransactionMapper;
import it.poc.fabrick.model.dto.MoneyTransferDto;
import it.poc.fabrick.model.dto.ResponseTransactionDto;
import it.poc.fabrick.model.dto.TransactionDto;
import it.poc.fabrick.model.entities.Transaction;
import it.poc.fabrick.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FabrickService {

    @Autowired
    private YamlConfig yamlConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    public ResponseEntity<?> getBalanceService(String accountId){

        ResponseEntity<String> response = null;

        log.info("FabrickService - GetBalanceService - START");

        try{
            response = restTemplate.exchange(
                    yamlConfig.getBaseUrl() + accountId + "/balance",
                    HttpMethod.GET,
                    createHeaders(),
                    String.class);

        }catch (Exception ex){
            log.info("FabrickService - GetBalanceService - ERROR");
            ObjectNode responseNode = objectMapper.createObjectNode();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            responseNode.put("message",ex.getMessage());
            if(Objects.isNull(response)){
                return new ResponseEntity<>(responseNode,HttpStatus.OK);
            }
        }

        log.info("FabrickService - GetBalanceService - END");
        return new ResponseEntity<>(response.getBody(),response.getStatusCode());
    }

    public ResponseEntity<?> createMoneyTransferService(String accountId, MoneyTransferDto moneyTransferDto){

        ResponseEntity<String> response = null;
        HttpEntity<MoneyTransferDto> requestEntity = new HttpEntity<>(moneyTransferDto, createHeaders().getHeaders());

        log.info("FabrickService - createMoneyTransferService - START");

        try{
            response = restTemplate.postForEntity(yamlConfig.getBaseUrl() + accountId + "/payments/money-transfers"
                    , requestEntity, String.class);

        }catch (Exception ex){
            log.info("FabrickService - createMoneyTransferService - ERROR");
            ObjectNode responseNode = objectMapper.createObjectNode();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            responseNode.put("message",ex.getMessage());
            if(Objects.isNull(response)){
                return new ResponseEntity<>(responseNode,HttpStatus.OK);
            }
        }

        log.info("FabrickService - createMoneyTransferService - END");
        return new ResponseEntity<>(response.getBody(),response.getStatusCode());
    }

    public ResponseEntity<?> getTransactionsService(String accountId, LocalDate fromAccountingDate, LocalDate toAccountingDate) throws JsonProcessingException {

        ResponseEntity<String> response = null;

        log.info("FabrickService - getTransactionsService - START");

        try{
            response = restTemplate.exchange(
                    yamlConfig.getBaseUrl() + accountId + "/transactions?fromAccountingDate="
                            + fromAccountingDate + "&toAccountingDate=" + toAccountingDate,
                    HttpMethod.GET,
                    createHeaders(),
                    String.class);

        }catch (Exception ex){
            log.info("FabrickService - getTransactionsService - ERROR");
            ObjectNode responseNode = objectMapper.createObjectNode();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            responseNode.put("message",ex.getMessage());
            if(Objects.isNull(response)){
                return new ResponseEntity<>(responseNode,HttpStatus.OK);
            }
        }

        List<TransactionDto> responseDto = objectMapper.readValue(response.getBody(), ResponseTransactionDto.class).getPayload().getList();
        //salvataggio sul DB delle transazioni
        log.info("FabrickService - getTransactionsService - Inizio Salvataggio sul DB delle transazioni");
        transactionService.saveTransactions(responseDto);
        log.info("FabrickService - getTransactionsService - Fine Salvataggio sul DB delle transazioni");

        log.info("FabrickService - getTransactionsService - END");
        return new ResponseEntity<>(response.getBody(),response.getStatusCode());
    }

    public List<TransactionDto> getAllTransactionsFromDB(){

        List<Transaction> transactionList = transactionRepository.findAll();
        log.info("FabrickService - getAllTransactionsFromDB - Recuperati " + transactionList.size() + " elementi");
        return transactionMapper.listModelToListDto(transactionRepository.findAll());
    }

    //Funzione utilizzata per la creazione dei vari headers alle API Rest Fabrick
    private HttpEntity<String> createHeaders(){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Auth-Schema", yamlConfig.getAuthSchema());
        httpHeaders.set("Api-Key", yamlConfig.getApiKey());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-Time-Zone",yamlConfig.getTimeZone());
        return new HttpEntity<>(httpHeaders);
    }
}
