package org.wv.stepsovc.web.controllers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wv.stepsovc.web.domain.Beneficiary;
import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.repository.AllBeneficiaries;
import org.wv.stepsovc.web.repository.AllReferrals;
import org.wv.stepsovc.web.request.BeneficiaryCase;

import static junit.framework.Assert.assertNotNull;
import static org.wv.stepsovc.web.mapper.ReferralMapperTest.*;
import static org.wv.stepsovc.web.request.CaseUpdateType.*;

public class StepsovcCaseControllerIntegrationTest {

    StepsovcCaseController stepsovcCaseController;

    AllBeneficiaries allBeneficiaries;

    AllReferrals allReferrals;

    private String beneficiaryCode = "8888";

    private Beneficiary beneficiary;

    public static final String APPLICATION_CONTEXT_XML = "applicationContext-Web.xml";

    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML);
        stepsovcCaseController = (StepsovcCaseController) context.getBean("stepsovcCaseController");
        allBeneficiaries = (AllBeneficiaries) context.getBean("allBeneficiaries");
        allReferrals = (AllReferrals) context.getBean("allReferrals");
    }

    @Test
    public void shouldCreateBeneficiaryReferralAndUpdateReferral() {

        BeneficiaryCase beneficiaryCase = createNewCase(beneficiaryCode);

        beneficiaryCase.setForm_type(BENEFICIARY_REGISTRATION.getType());
        stepsovcCaseController.createCase(beneficiaryCase);
        beneficiary = allBeneficiaries.findBeneficiary(beneficiaryCode);
        assertNotNull(beneficiary);

        beneficiaryCase = createCaseForReferral(beneficiaryCode, "2012-4-12");

        beneficiaryCase.setForm_type(NEW_REFERRAL.getType());
        stepsovcCaseController.createCase(beneficiaryCase);
        Referral activeReferral = allReferrals.findActiveReferral(beneficiaryCode);
        assertNotNull(activeReferral);
        assertReferrals(beneficiaryCase, allReferrals.findActiveReferral(beneficiaryCode));


        beneficiaryCase.setService_date("2012-5-12");
        stepsovcCaseController.createCase(beneficiaryCase);
        activeReferral = allReferrals.findActiveReferral(beneficiaryCode);
        assertNotNull(activeReferral);
        assertReferrals(beneficiaryCase, allReferrals.findActiveReferral(beneficiaryCode));

        beneficiaryCase = createCaseForUpdateService(beneficiaryCode, "12-12-1987");
        beneficiaryCase.setForm_type(UPDATE_SERVICE.getType());
        stepsovcCaseController.createCase(beneficiaryCase);
        Assert.assertNotNull(allReferrals.findActiveReferral(beneficiaryCode));
        assertServices(beneficiaryCase, allReferrals.findActiveReferral(beneficiaryCode));

        beneficiaryCase = createCaseForUpdateReferral(beneficiaryCode);
        beneficiaryCase.setForm_type(UPDATE_REFERRAL.getType());
        stepsovcCaseController.createCase(beneficiaryCase);
        Assert.assertNotNull(allBeneficiaries.findBeneficiary(beneficiaryCode));
        assertReferralReasons(beneficiaryCase, allReferrals.findActiveReferral(beneficiaryCode));

    }


    @After
    public void clearAll() throws SchedulerException {
        allBeneficiaries.remove(beneficiary);
    }

}
