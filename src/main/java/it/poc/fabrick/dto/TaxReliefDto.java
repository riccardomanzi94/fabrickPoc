package it.poc.fabrick.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaxReliefDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private boolean isCondoUpgrade;
    private String creditorFiscalCode;
    private String beneficiaryType;
    private NaturalPersonBeneficiaryDto naturalPersonBeneficiary;
    private LegalPersonBeneficiaryDto legalPersonBeneficiary;
}
