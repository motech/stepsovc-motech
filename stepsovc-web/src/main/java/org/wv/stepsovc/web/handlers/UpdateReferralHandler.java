package org.wv.stepsovc.web.handlers;

import org.apache.log4j.Logger;
import org.wv.stepsovc.web.request.BeneficiaryCase;

public class UpdateReferralHandler {

    private static Logger logger = Logger.getLogger(BeneficiaryRegistrationHandler.class);

    public void handleCase(BeneficiaryCase beneficiaryCase) {
        logger.info("Handling update referral");
    }
}
