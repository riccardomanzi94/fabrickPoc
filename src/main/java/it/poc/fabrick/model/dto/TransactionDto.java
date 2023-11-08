package it.poc.fabrick.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TransactionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String operationId;
    private LocalDate accountingDate;
    private LocalDate valueDate;
    @JsonIgnore
    private CustomType type;
    private Integer amount;
    private String currency;
    private String description;

}
