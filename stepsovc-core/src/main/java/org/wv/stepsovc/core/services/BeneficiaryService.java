package org.wv.stepsovc.core.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.CaseOwnershipInformation;
import org.wv.stepsovc.core.domain.Beneficiary;
import org.wv.stepsovc.core.domain.Caregiver;
import org.wv.stepsovc.core.exception.BeneficiaryNotFoundException;
import org.wv.stepsovc.core.mapper.BeneficiaryMapper;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.repository.AllCaregivers;
import org.wv.stepsovc.core.request.StepsovcCase;

@Service
public class BeneficiaryService {

    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    AllBeneficiaries allBeneficiaries;
    @Autowired
    private CommcareGateway commcareGateway;
    @Autowired
    private AllCaregivers allCaregivers;

    public void createBeneficiary(StepsovcCase stepsovcCase) {
        logger.info("Handling beneficiary registration for " + stepsovcCase.getBeneficiary_name());

        Beneficiary oldBeneficiary = allBeneficiaries.findBeneficiaryByCode(stepsovcCase.getBeneficiary_code());
        if (oldBeneficiary == null)
            allBeneficiaries.add(new BeneficiaryMapper().map(stepsovcCase));
        else {
            logger.error("Updating as Beneficiary already present in database, " + stepsovcCase.getBeneficiary_code());
            allBeneficiaries.remove(oldBeneficiary);
            allBeneficiaries.add(new BeneficiaryMapper().map(stepsovcCase));
        }
    }

    public void addUserOwnership(StepsovcCase stepsovcCase) {
        Beneficiary beneficiary = allBeneficiaries.findBeneficiaryByCode(stepsovcCase.getBeneficiary_code());
        Caregiver caregiver = allCaregivers.findCaregiverByCode(stepsovcCase.getCaregiver_code());

        if (beneficiary != null && caregiver != null) {
            commcareGateway.addUserOwnership(populateBeneficiaryCase(stepsovcCase, beneficiary), caregiver.getCgId());
        }
    }

    public void addGroupOwnership(StepsovcCase stepsovcCase) {
        Beneficiary beneficiary = allBeneficiaries.findBeneficiaryByCode(stepsovcCase.getBeneficiary_code());

        if (beneficiary != null) {
            commcareGateway.addGroupOwnership(populateBeneficiaryCase(stepsovcCase, beneficiary), stepsovcCase.getFacility_code());
        }
    }

    private CaseOwnershipInformation populateBeneficiaryCase(StepsovcCase stepsovcCase, Beneficiary beneficiary) {
        stepsovcCase.setCaregiver_code(beneficiary.getCaregiverCode());
        stepsovcCase.setCase_id(beneficiary.getCaseId());

        Caregiver caregiver = allCaregivers.findCaregiverByCode(beneficiary.getCaregiverCode());
        stepsovcCase.setCaregiver_name(caregiver.getFirstName());
        stepsovcCase.setUser_id(caregiver.getCgId());
        stepsovcCase.setOwner_id(caregiver.getCgId());

        return new BeneficiaryMapper().createOwnershipInfo(stepsovcCase);
    }

    public void changeOwnershipForRequestOwnershipCase(StepsovcCase stepsovcCase) {
        commcareGateway.addGroupOwnership(new BeneficiaryMapper().createOwnershipInfo(stepsovcCase), CommcareGateway.ALL_USERS_GROUP);
    }

    public String getBeneficiaryId(String beneficiaryCode) {
        Beneficiary beneficiary = allBeneficiaries.findBeneficiaryByCode(beneficiaryCode);
        if (beneficiary == null) {
            throw new BeneficiaryNotFoundException(beneficiaryCode);
        }
        return beneficiary.getCaseId();
    }

    public boolean beneficiaryExists(String beneficiaryCode){
        return allBeneficiaries.findBeneficiaryByCode(beneficiaryCode) != null;
    }

}
