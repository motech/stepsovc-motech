package org.wv.stepsovc.web.handlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.wv.stepsovc.web.mapper.ReferralMapper;
import org.wv.stepsovc.web.repository.AllReferrals;
import org.wv.stepsovc.web.request.BeneficiaryCase;

import java.text.ParseException;

public class NewReferralHandler {
    private static Logger logger = Logger.getLogger(BeneficiaryRegistrationHandler.class);

    @Autowired
    private AllReferrals allReferrals;

    public void handleCase(BeneficiaryCase beneficiaryCase) throws ParseException {
        logger.info("Handling new referral");
        allReferrals.add(new ReferralMapper().map(beneficiaryCase));
    }
}
