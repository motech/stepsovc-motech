package org.wv.stepsovc.web.mapper;

import org.wv.stepsovc.vo.BeneficiaryInformation;
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

    public BeneficiaryInformation createFormRequest(StepsovcCase stepsovcCase) {

        BeneficiaryInformation beneficiaryInformation = new BeneficiaryInformation();
        beneficiaryInformation.setCareGiverCode(stepsovcCase.getCaregiver_code());
        beneficiaryInformation.setCareGiverName(stepsovcCase.getCaregiver_name());

        beneficiaryInformation.setBeneficiaryId(stepsovcCase.getCase_id());
        beneficiaryInformation.setDateModified(stepsovcCase.getDate_modified());
        beneficiaryInformation.setCareGiverId(stepsovcCase.getUser_id());
        beneficiaryInformation.setOwnerId(stepsovcCase.getOwner_id());

        return beneficiaryInformation;
    }
}
