package it.poc.fabrick.mapper;

import it.poc.fabrick.model.dto.TransactionDto;
import it.poc.fabrick.model.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper( TransactionMapper.class );

    Transaction dtoToModel(TransactionDto transactionDto);

    List<Transaction> listDtoToListModel(List<TransactionDto> transactionDtoList);


}
