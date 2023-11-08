package it.poc.fabrick.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreditorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;

    private AccountDto account;

    private AddressDto address;
}
