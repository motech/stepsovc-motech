package org.wv.stepsovc.web.repository;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.appointments.api.repository.AllAppointmentCalendars;
import org.motechproject.appointments.api.repository.AllReminderJobs;
import org.motechproject.appointments.api.service.contract.VisitResponse;
import org.motechproject.appointments.api.service.contract.VisitsQuery;
import org.motechproject.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.web.BaseScheduleTest;
import org.wv.stepsovc.web.TestJobDetail;
import org.wv.stepsovc.web.domain.Referral;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.motechproject.util.DateUtil.newDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:testApplicationContext.xml")
public class AllAppointmentsIT extends BaseScheduleTest {

    @Autowired
    AllAppointments allAppointments;
    @Autowired
    private AllAppointmentCalendars allAppointmentCalendars;
    @Autowired
    private AllReminderJobs allReminderJobs;

    @Test
    public void shouldCreateAppointmentForReferral() {
        String externalId = "9678904";
        String facilityId = "123";
        DateTime dueDate = DateUtil.newDateTime(2012, 2, 2, 9, 8, 58);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Referral.META_FACILITY_ID, facilityId);

        allAppointments.scheduleNewReferral(externalId, params, dueDate);

        List<VisitResponse> visitResponses = allAppointments.find(new VisitsQuery().havingExternalId(externalId));
        assertReferralAppointments(externalId, dueDate, params, visitResponses);
        List<TestJobDetail> testJobDetails = captureAlertsForReferral(externalId, 3);
        assertAlerts(testJobDetails, asList(
                alert(newDateTime(2012, 2, 1, 0, 0, 0)),
                alert(newDateTime(2012, 2, 2, 0, 0, 0)),
                alert(newDateTime(2012, 2, 3, 0, 0, 0))
        ));
    }

    public static void assertReferralAppointments(String externalId, DateTime dueDate, Map<String, Object> params, List<VisitResponse> visitResponses) {
        assertThat(visitResponses.size(), is(1));
        assertThat(visitResponses.get(0).getExternalId(), is(externalId));
        assertThat(visitResponses.get(0).getAppointmentDueDate(), is(dueDate));
        assertThat(visitResponses.get(0).getVisitData(), is(params));
    }

    @After
    public void tearDown() {
        allAppointmentCalendars.removeAll();
    }
}
