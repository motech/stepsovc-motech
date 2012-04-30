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
import org.wv.stepsovc.web.request.CaseUpdateType;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.wv.stepsovc.web.mapper.ReferralMapperTest.createCaseForReferral;
import static org.wv.stepsovc.web.mapper.ReferralMapperTest.createCaseForUpdateService;
import static org.wv.stepsovc.web.mapper.ReferralMapperTest.createNewCase;

public class StepsovcCaseControllerIT {

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
    public void shouldCreateBeneficiary(){
        BeneficiaryCase beneficiaryCase = createNewCase(beneficiaryCode);

        beneficiaryCase.setForm_type(CaseUpdateType.BENEFICIARY_REGISTRATION.getType());
        stepsovcCaseController.createCase(beneficiaryCase);
        beneficiary = allBeneficiaries.findBeneficiary(beneficiaryCode);
        assertNotNull(beneficiary);

        beneficiaryCase = createCaseForReferral(beneficiaryCode, "2012-4-12");

        beneficiaryCase.setForm_type(CaseUpdateType.NEW_REFERRAL.getType());
        stepsovcCaseController.createCase(beneficiaryCase);
        Referral activeReferral = allReferrals.findActiveReferral(beneficiaryCode);
        assertNotNull(activeReferral);
        assertThat(activeReferral.getServiceDate(), is("2012-4-12"));


        beneficiaryCase.setService_date("2012-5-12");
        stepsovcCaseController.createCase(beneficiaryCase);
        activeReferral = allReferrals.findActiveReferral(beneficiaryCode);
        assertNotNull(activeReferral);
        assertThat(activeReferral.getServiceDate(), is("2012-5-12"));

        beneficiaryCase = createCaseForUpdateService(beneficiaryCode,"12-12-1987");
        beneficiaryCase.setForm_type(CaseUpdateType.UPDATE_SERVICE.getType());
        stepsovcCaseController.createCase(beneficiaryCase);
        Assert.assertNotNull(allReferrals.findActiveReferral(beneficiaryCode));
        assertThat(allReferrals.findActiveReferral(beneficiaryCode).getVisitDate(),is("12-12-1987"));

    }



    @After
    public void clearAll() throws SchedulerException {
        allBeneficiaries.remove(beneficiary);
    }

}
