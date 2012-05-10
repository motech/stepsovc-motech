package org.wv.stepsovc.web.controllers;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.web.repository.AllFacilities;
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
    private String facilityId = "testId";

    @Test
    public void shouldCreateOrUpdateFacilityWithServiceUnavailability() {
        StepsovcCase stepsovcCase = createFacilityCase(facilityId, "2012-01-01", "2012-01-01");

        assertNull(allFacilities.findFacilityById(facilityId));
        stepsovcCaseController.createCase(stepsovcCase);
        assertNotNull(allFacilities.findFacilityById(facilityId));

        stepsovcCase = createFacilityCase(facilityId, "2012-02-01", "2012-02-01");
        stepsovcCaseController.createCase(stepsovcCase);

        assertThat(2, is(allFacilities.findFacilityById(facilityId).getServiceUnavailabilities().size()));
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
    }
}
