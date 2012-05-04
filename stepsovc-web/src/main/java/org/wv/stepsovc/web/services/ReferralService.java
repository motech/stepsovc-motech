package org.wv.stepsovc.web.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.repository.AllGroups;
import org.wv.stepsovc.vo.BeneficiaryFormRequest;
import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.mapper.BeneficiaryMapper;
import org.wv.stepsovc.web.mapper.ReferralMapper;
import org.wv.stepsovc.web.repository.AllReferrals;
import org.wv.stepsovc.web.request.BeneficiaryCase;

public class ReferralService {

    private static Logger logger = Logger.getLogger(BeneficiaryService.class);

    @Autowired
    private AllReferrals allReferrals;
    @Autowired
    private AllGroups allGroups;

    @Autowired
    private CommcareGateway commcareGateway;

    private String COMMCARE_URL;

    public ReferralService(String COMMCARE_URL) {
        this.COMMCARE_URL = COMMCARE_URL;
    }

    public void addNewReferral(BeneficiaryCase beneficiaryCase) {
        logger.info("Handling new referral");
        Referral oldActiveReferral = allReferrals.findActiveReferral(beneficiaryCase.getBeneficiary_code());
        if(oldActiveReferral != null) {
            oldActiveReferral.setActive(false);
            allReferrals.update(oldActiveReferral);
        }

        Referral newReferral = new ReferralMapper().map(beneficiaryCase);
        newReferral.setActive(true);

        allReferrals.add(newReferral);

        String groupId = allGroups.getIdByName(beneficiaryCase.getService_provider());
        String ownerId = beneficiaryCase.getUser_id() + "," + groupId;
        beneficiaryCase.setOwner_id(ownerId);
        updateReferralOwner(beneficiaryCase);
    }

    public void updateNotAvailedReasons(BeneficiaryCase beneficiaryCase) {
        logger.info("Handling update referral");
        Referral existingReferral = allReferrals.findActiveReferral(beneficiaryCase.getBeneficiary_code());

        allReferrals.update(new ReferralMapper().updateReferral(existingReferral, beneficiaryCase));
    }

    public void updateAvailedServices(BeneficiaryCase beneficiaryCase) {
        logger.info("Handling update service");
        Referral existingReferral = allReferrals.findActiveReferral(beneficiaryCase.getBeneficiary_code());

        allReferrals.update(new ReferralMapper().updateServices(existingReferral, beneficiaryCase));
    }

    public void updateReferralOwner(BeneficiaryCase beneficiaryCase) {
        BeneficiaryFormRequest beneficiaryFormRequest = new BeneficiaryMapper().createFormRequest(beneficiaryCase);
        commcareGateway.submitOwnerUpdateForm(COMMCARE_URL, beneficiaryFormRequest);

    }
}
