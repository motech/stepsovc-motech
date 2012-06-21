package org.wv.stepsovc.core.exception;

public class BeneficiaryNotFoundException extends RuntimeException {

    public BeneficiaryNotFoundException(String beneficiaryCode) {
        super("Beneficiary Not Found For Code " + beneficiaryCode);
    }

}
