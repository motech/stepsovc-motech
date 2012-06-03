package org.wv.stepsovc.web.controllers;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.appointments.api.repository.AllAppointmentCalendars;
import org.motechproject.appointments.api.service.contract.VisitResponse;
import org.motechproject.appointments.api.service.contract.VisitsQuery;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.core.domain.Beneficiary;
import org.wv.stepsovc.core.domain.Facility;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.ServiceUnavailability;
import org.wv.stepsovc.core.repository.AllAppointments;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.repository.AllFacilities;
import org.wv.stepsovc.core.repository.AllReferrals;
import org.wv.stepsovc.core.request.StepsovcCase;
import org.wv.stepsovc.core.utils.DateUtils;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.wv.stepsovc.core.request.CaseUpdateType.*;
import static org.wv.stepsovc.web.controllers.StepsovcCaseFixture.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:testWebApplicationContext.xml")
public class BeneficiaryCaseIT {

    @Autowired
    StepsovcCaseController stepsovcCaseController;
    @Autowired
    AllBeneficiaries allBeneficiaries;
    @Autowired
    AllReferrals allReferrals;

    private String beneficiaryCode = "8888";

    private Beneficiary beneficiary;

    @Autowired
    private AllAppointments allAppointments;

    @Autowired
    private AllAppointmentCalendars allAppointmentCalendars;
    @Autowired
    private AllFacilities allFacilities;
    private Facility facility;

    @Test
    public void shouldCreateBeneficiaryReferralAndUpdateReferral() {

        StepsovcCase stepsovcCase = createNewCase(beneficiaryCode);

        stepsovcCase.setForm_type(BENEFICIARY_REGISTRATION.getType());
        stepsovcCaseController.createCase(stepsovcCase);
        beneficiary = allBeneficiaries.findBeneficiaryByCode(beneficiaryCode);
        assertNotNull(beneficiary);

        facility = new Facility("FAC001", "FAC001-Name",
                asList(new ServiceUnavailability("reason1", "2012-06-20", "2012-06-20"),
                        new ServiceUnavailability("reason2", "2012-06-26", "2012-06-26")
                ),
                asList("9999999999", "88888888"));
        allFacilities.add(facility);

        stepsovcCase = createCaseForReferral(beneficiaryCode, "2012-4-12", "FAC001");

        stepsovcCase.setForm_type(NEW_REFERRAL.getType());
        stepsovcCaseController.createCase(stepsovcCase);
        Referral activeReferral = allReferrals.findActiveReferral(beneficiaryCode);
        assertNotNull(activeReferral);
        assertReferrals(stepsovcCase, activeReferral);

        String firstOvcId = activeReferral.getOvcId();
        List<VisitResponse> visitResponses = allAppointments.find(new VisitsQuery().havingExternalId(firstOvcId));
        assertReferralAppointments(activeReferral.getOvcId(), DateUtils.getDateTime(activeReferral.getServiceDate()), activeReferral.appointmentDataMap(), visitResponses);

        stepsovcCase.setService_date("2012-5-12");
        stepsovcCaseController.createCase(stepsovcCase);
        activeReferral = allReferrals.findActiveReferral(beneficiaryCode);
        String secondOvcId = activeReferral.getOvcId();
        assertNotNull(activeReferral);
        assertReferrals(stepsovcCase, allReferrals.findActiveReferral(beneficiaryCode));
        visitResponses = allAppointments.find(new VisitsQuery().havingExternalId(firstOvcId));
        assertThat(visitResponses.size(), is(0));
        visitResponses = allAppointments.find(new VisitsQuery().havingExternalId(secondOvcId));
        assertThat(visitResponses.size(), is(1));

        stepsovcCase = createCaseForUpdateService(beneficiaryCode, "1987-12-12", "");
        stepsovcCase.setForm_type(UPDATE_SERVICE.getType());
        stepsovcCaseController.createCase(stepsovcCase);
        Assert.assertNotNull(allReferrals.findActiveReferral(beneficiaryCode));
        assertServices(stepsovcCase, allReferrals.findActiveReferral(beneficiaryCode));
        visitResponses = allAppointments.find(new VisitsQuery().havingExternalId(secondOvcId));
        assertThat(visitResponses.size(), is(0));

        stepsovcCase = createCaseForUpdateReferral(beneficiaryCode);
        stepsovcCase.setForm_type(UPDATE_REFERRAL.getType());
        stepsovcCaseController.createCase(stepsovcCase);
        Assert.assertNotNull(allBeneficiaries.findBeneficiaryByCode(beneficiaryCode));
        assertReferralReasons(stepsovcCase, allReferrals.findActiveReferral(beneficiaryCode));

    }

    public static void assertReferralAppointments(String externalId, DateTime dueDate, Map<String, Object> params, List<VisitResponse> visitResponses) {
        assertThat(visitResponses.size(), is(1));
        assertThat(visitResponses.get(0).getExternalId(), is(externalId));
        assertThat(visitResponses.get(0).getAppointmentDueDate(), is(dueDate));
        assertThat(visitResponses.get(0).getVisitData(), is(params));
    }

    @After
    public void clearAll() throws SchedulerException {
        allFacilities.remove(facility);
        allBeneficiaries.removeAll();
        allReferrals.removeAllByBeneficiary(beneficiaryCode);
        allAppointmentCalendars.removeAll();
    }

}
