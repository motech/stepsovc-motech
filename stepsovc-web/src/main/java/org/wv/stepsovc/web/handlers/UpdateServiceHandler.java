package org.wv.stepsovc.web.handlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.mapper.ReferralMapper;
import org.wv.stepsovc.web.repository.AllReferrals;
import org.wv.stepsovc.web.request.BeneficiaryCase;

public class UpdateServiceHandler {

    private static Logger logger = Logger.getLogger(BeneficiaryRegistrationHandler.class);

    @Autowired
    private AllReferrals allReferrals;

    public void handleCase(BeneficiaryCase beneficiaryCase) {
        logger.info("Handling update service");
        Referral existingReferral = allReferrals.findActiveReferral(beneficiaryCase.getBeneficiary_code());

        allReferrals.update(new ReferralMapper().updateServices(existingReferral, beneficiaryCase));
    }
}
