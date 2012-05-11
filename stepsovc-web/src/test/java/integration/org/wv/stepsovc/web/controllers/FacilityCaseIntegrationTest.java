package org.wv.stepsovc.web.controllers;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.web.mapper.ReferralMapperTest;
import org.wv.stepsovc.web.repository.AllFacilities;
import org.wv.stepsovc.web.repository.AllReferrals;
import org.wv.stepsovc.web.request.BeneficiaryCaseUpdateType;
import org.wv.stepsovc.web.request.CaseType;
import org.wv.stepsovc.web.request.StepsovcCase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-Web.xml")
public class FacilityCaseIntegrationTest {

    @Autowired
    StepsovcCaseController stepsovcCaseController;

    @Autowired
    AllFacilities allFacilities;
    @Autowired
    AllReferrals allReferrals;
    private String facilityId = "testId";
    private String benCode1 = "BEN001";
    private String benCode2 = "BEN002";
    private String benCode3 = "BEN003";

    @Test
    public void shouldCreateOrUpdateFacilityWithServiceUnavailability() {
        String mayFirst = "2012-05-01";
        String maySecond = "2012-05-02";
        StepsovcCase caseForReferral1 = ReferralMapperTest.createCaseForReferral(benCode1, mayFirst, facilityId);
        StepsovcCase caseForReferral2 = ReferralMapperTest.createCaseForReferral(benCode2, mayFirst, facilityId);
        StepsovcCase caseForReferral3 = ReferralMapperTest.createCaseForReferral(benCode3, "2012-01-03", facilityId);
        StepsovcCase facilityCase = createFacilityCase(facilityId, mayFirst, mayFirst);

        caseForReferral1.setForm_type(BeneficiaryCaseUpdateType.NEW_REFERRAL.getType());
        caseForReferral2.setForm_type(BeneficiaryCaseUpdateType.NEW_REFERRAL.getType());
        caseForReferral3.setForm_type(BeneficiaryCaseUpdateType.NEW_REFERRAL.getType());

        stepsovcCaseController.createCase(caseForReferral1);
        stepsovcCaseController.createCase(caseForReferral2);
        stepsovcCaseController.createCase(caseForReferral3);

        int maySecondReferralsBefore = allReferrals.findActiveReferrals(facilityId,maySecond).size();
        assertNull(allFacilities.findFacilityById(facilityId));

        stepsovcCaseController.createCase(facilityCase);
        assertNotNull(allFacilities.findFacilityById(facilityId));

        int maySecondReferralsAfter = allReferrals.findActiveReferrals(facilityId,maySecond).size();
        assertThat(maySecondReferralsAfter, is(maySecondReferralsBefore+2));

        facilityCase = createFacilityCase(facilityId, "2012-02-01", "2012-02-01");
        stepsovcCaseController.createCase(facilityCase);
        assertThat(allFacilities.findFacilityById(facilityId).getServiceUnavailabilities().size(), is(2));

        StepsovcCase caseForReferral4 = ReferralMapperTest.createCaseForReferral(benCode3, "2012-02-01", facilityId);
        caseForReferral4.setForm_type(BeneficiaryCaseUpdateType.NEW_REFERRAL.getType());

        stepsovcCaseController.createCase(caseForReferral4);

        assertThat(allReferrals.findActiveReferral(benCode3).getServiceDate(),is("2012-02-02"));

    }

    private StepsovcCase createFacilityCase(String facilityId, String from, String toDate) {
        StepsovcCase stepsovcCase = new StepsovcCase();
        stepsovcCase.setCase_type(CaseType.FACILITY_CASE.getType());
        stepsovcCase.setFacility_id(facilityId);
        stepsovcCase.setFacility_name("testName");
        stepsovcCase.setService_unavailable_reason("testReason");
        stepsovcCase.setService_unavailable_from(from);
        stepsovcCase.setService_unavailable_to(toDate);
        return stepsovcCase;
    }

    @After
    public void clearAll() throws SchedulerException {
        allFacilities.remove(allFacilities.findFacilityById(facilityId));
        allReferrals.removeAllByBeneficiary(benCode1);
        allReferrals.removeAllByBeneficiary(benCode2);
        allReferrals.removeAllByBeneficiary(benCode3);
    }
}
