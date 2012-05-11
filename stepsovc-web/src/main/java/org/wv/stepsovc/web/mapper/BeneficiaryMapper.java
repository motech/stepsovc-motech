package org.wv.stepsovc.web.mapper;

import org.wv.stepsovc.vo.*;
import org.wv.stepsovc.web.domain.Beneficiary;
import org.wv.stepsovc.web.request.StepsovcCase;

public class BeneficiaryMapper {

    public Beneficiary map(StepsovcCase stepsovcCase) {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setCaregiverCode(stepsovcCase.getCaregiver_code());
        beneficiary.setCode(stepsovcCase.getBeneficiary_code());
        beneficiary.setDateOfBirth(stepsovcCase.getBeneficiary_dob());
        beneficiary.setName(stepsovcCase.getBeneficiary_name());
        beneficiary.setSex(stepsovcCase.getSex());
        beneficiary.setTitle(stepsovcCase.getTitle());

        return beneficiary;
    }

    public BeneficiaryFormRequest createFormRequest(StepsovcCase stepsovcCase) {

        BeneficiaryFormRequest beneficiaryFormRequest = new BeneficiaryFormRequest();
        final BeneficiaryInformation beneficiaryInformation = new BeneficiaryInformation();
        final CareGiverInformation careGiverInformation = new CareGiverInformation();
        careGiverInformation.setCode(stepsovcCase.getCaregiver_code());
        careGiverInformation.setName(stepsovcCase.getCaregiver_name());

        final CaseInformation caseInformation = new CaseInformation();
        caseInformation.setId(stepsovcCase.getCase_id());
        caseInformation.setDateModified(stepsovcCase.getDate_modified());
        caseInformation.setUserId(stepsovcCase.getUser_id());
        caseInformation.setOwnerId(stepsovcCase.getOwner_id());

        final MetaInformation metaInformation = new MetaInformation();

        beneficiaryFormRequest.setBeneficiaryInformation(beneficiaryInformation);
        beneficiaryFormRequest.setCaregiverInformation(careGiverInformation);
        beneficiaryFormRequest.setCaseInformation(caseInformation);
        beneficiaryFormRequest.setMetaInformation(metaInformation);

        return beneficiaryFormRequest;
    }
}
