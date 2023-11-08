package it.poc.fabrick.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class MoneyTransferDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private CreditorDto creditor;
    private LocalDate executionDate;
    private String uri;
    //required
    @NotNull
    private String description;
    @NotNull
    private Integer amount;
    @NotNull
    private String currency;

    private Boolean isUrgent;
    private Boolean isInstant;
    private String feeType;
    private String feeAccountId;
    private TaxReliefDto taxRelief;

}
