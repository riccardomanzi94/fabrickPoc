package it.poc.fabrick.service;

import it.poc.fabrick.mapper.TransactionMapper;
import it.poc.fabrick.model.dto.TransactionDto;
import it.poc.fabrick.model.entities.Transaction;
import it.poc.fabrick.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TransactionService {


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    public void saveTransactions(List<TransactionDto> transactionDtoList){

        List<Transaction> transactionList = transactionMapper.listDtoToListModel(transactionDtoList);

        log.info("TransactionService - presenti: " + transactionList.size() + "elementi da salvare");

        for (Transaction t: transactionList) {
           transactionRepository.save(t);
        }
    }


}
