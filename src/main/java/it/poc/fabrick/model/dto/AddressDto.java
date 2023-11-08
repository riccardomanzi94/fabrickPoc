package it.poc.fabrick.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String address;
    private String city;
    private String countryCode;
}
