package org.wv.stepsovc.web.controllers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.web.domain.Beneficiary;
import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.repository.AllBeneficiaries;
import org.wv.stepsovc.web.repository.AllReferrals;
import org.wv.stepsovc.web.request.StepsovcCase;

import static junit.framework.Assert.assertNotNull;
import static org.wv.stepsovc.web.mapper.ReferralMapperTest.*;
import static org.wv.stepsovc.web.request.BeneficiaryCaseUpdateType.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-Web.xml")
public class BeneficiaryCaseIntegrationTest {

    @Autowired
    StepsovcCaseController stepsovcCaseController;
    @Autowired
    AllBeneficiaries allBeneficiaries;
    @Autowired
    AllReferrals allReferrals;

    private String beneficiaryCode = "8888";

    private Beneficiary beneficiary;

    @Test
    public void shouldCreateBeneficiaryReferralAndUpdateReferral() {

        StepsovcCase stepsovcCase = createNewCase(beneficiaryCode);

        stepsovcCase.setForm_type(BENEFICIARY_REGISTRATION.getType());
        stepsovcCaseController.createCase(stepsovcCase);
        beneficiary = allBeneficiaries.findBeneficiary(beneficiaryCode);
        assertNotNull(beneficiary);

        stepsovcCase = createCaseForReferral(beneficiaryCode, "2012-4-12");

        stepsovcCase.setForm_type(NEW_REFERRAL.getType());
        stepsovcCaseController.createCase(stepsovcCase);
        Referral activeReferral = allReferrals.findActiveReferral(beneficiaryCode);
        assertNotNull(activeReferral);
        assertReferrals(stepsovcCase, allReferrals.findActiveReferral(beneficiaryCode));


        stepsovcCase.setService_date("2012-5-12");
        stepsovcCaseController.createCase(stepsovcCase);
        activeReferral = allReferrals.findActiveReferral(beneficiaryCode);
        assertNotNull(activeReferral);
        assertReferrals(stepsovcCase, allReferrals.findActiveReferral(beneficiaryCode));

        stepsovcCase = createCaseForUpdateService(beneficiaryCode, "12-12-1987");
        stepsovcCase.setForm_type(UPDATE_SERVICE.getType());
        stepsovcCaseController.createCase(stepsovcCase);
        Assert.assertNotNull(allReferrals.findActiveReferral(beneficiaryCode));
        assertServices(stepsovcCase, allReferrals.findActiveReferral(beneficiaryCode));

        stepsovcCase = createCaseForUpdateReferral(beneficiaryCode);
        stepsovcCase.setForm_type(UPDATE_REFERRAL.getType());
        stepsovcCaseController.createCase(stepsovcCase);
        Assert.assertNotNull(allBeneficiaries.findBeneficiary(beneficiaryCode));
        assertReferralReasons(stepsovcCase, allReferrals.findActiveReferral(beneficiaryCode));

    }

    @After
    public void clearAll() throws SchedulerException {
        allBeneficiaries.remove(beneficiary);
        allReferrals.removeAllByBeneficiary(beneficiaryCode);
    }

}
