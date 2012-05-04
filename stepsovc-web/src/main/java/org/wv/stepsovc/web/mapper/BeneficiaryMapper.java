package org.wv.stepsovc.web.mapper;

import org.wv.stepsovc.vo.*;
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

    public BeneficiaryFormRequest createFormRequest(BeneficiaryCase beneficiaryCase) {

        BeneficiaryFormRequest beneficiaryFormRequest = new BeneficiaryFormRequest();
        final BeneficiaryInformation beneficiaryInformation = new BeneficiaryInformation();
        final CareGiverInformation careGiverInformation = new CareGiverInformation();
        careGiverInformation.setId(beneficiaryCase.getCaregiver_id());
        careGiverInformation.setName(beneficiaryCase.getCaregiver_name());

        final CaseInformation caseInformation = new CaseInformation();
        caseInformation.setId(beneficiaryCase.getCase_id());
        caseInformation.setDateModified(beneficiaryCase.getDate_modified());
        caseInformation.setUserId(beneficiaryCase.getUser_id());
        caseInformation.setOwnerId(beneficiaryCase.getOwner_id());

        final MetaInformation metaInformation = new MetaInformation();

        beneficiaryFormRequest.setBeneficiaryInformation(beneficiaryInformation);
        beneficiaryFormRequest.setCaregiverInformation(careGiverInformation);
        beneficiaryFormRequest.setCaseInformation(caseInformation);
        beneficiaryFormRequest.setMetaInformation(metaInformation);

        return beneficiaryFormRequest;
    }
}
