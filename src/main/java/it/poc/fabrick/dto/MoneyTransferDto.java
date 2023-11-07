package it.poc.fabrick.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class MoneyTransferDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private CreditorDto creditor;
    private String description;
    private LocalDate executionDate;
    private String amount;
    private String currency;
    private TaxReliefDto taxRelief;

}
