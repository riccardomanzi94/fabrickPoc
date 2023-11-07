package it.poc.fabrick.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountCode;
    private String bicCode;
}
