package org.wv.stepsovc.web.mapper;

import org.wv.stepsovc.web.domain.Beneficiary;
import org.wv.stepsovc.web.request.BeneficiaryCase;

public class BeneficiaryMapper {

    public Beneficiary map(BeneficiaryCase beneficiaryCase) {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setCaregiverId(beneficiaryCase.getCaregiver_id());
        beneficiary.setCode(beneficiaryCase.getBeneficiary_code());
        beneficiary.setDateOfBirth(beneficiaryCase.getBeneficiary_dob());
        beneficiary.setName(beneficiaryCase.getBeneficiary_name());
        beneficiary.setSex(beneficiaryCase.getSex());
        beneficiary.setTitle(beneficiaryCase.getTitle());

        return beneficiary;
    }
}
