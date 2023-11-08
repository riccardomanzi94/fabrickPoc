package it.poc.fabrick.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class AccountDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String accountCode;
    private String bicCode;
}
