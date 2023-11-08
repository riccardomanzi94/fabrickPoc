package it.poc.fabrick.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseTransactionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;
    private List<String> error;
    private ListDto payload;

}
