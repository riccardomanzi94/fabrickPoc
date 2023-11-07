package it.poc.fabrick.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreditorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private AccountDto account;
}
