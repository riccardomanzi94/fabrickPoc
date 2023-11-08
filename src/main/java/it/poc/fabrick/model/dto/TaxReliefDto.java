package it.poc.fabrick.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class TaxReliefDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String taxReliefId;
    @NotNull
    private Boolean isCondoUpgrade;
    @NotNull
    private String creditorFiscalCode;
    @NotNull
    private String beneficiaryType;
    private NaturalPersonBeneficiaryDto naturalPersonBeneficiary;
    private LegalPersonBeneficiaryDto legalPersonBeneficiary;
}
