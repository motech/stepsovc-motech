package org.wv.stepsovc.web.repository;

import integration.org.wv.stepsovc.web.BaseScheduleTest;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.appointments.api.repository.AllAppointmentCalendars;
import org.motechproject.appointments.api.repository.AllReminderJobs;
import org.motechproject.appointments.api.service.AppointmentService;
import org.motechproject.appointments.api.service.contract.VisitResponse;
import org.motechproject.appointments.api.service.contract.VisitsQuery;
import org.motechproject.util.DateUtil;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.web.domain.Referral;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
        //TODO: assertions -
        String externalId = "9678904";
        String facilityId = "123";
        DateTime dueDate = DateUtil.newDateTime(2012, 2, 2, 9, 8, 0);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Referral.META_FACILITY_ID, facilityId);

        allAppointments.scheduleNewReferral(externalId, params, dueDate);

        List<VisitResponse> visitResponses = allAppointments.find(new VisitsQuery().havingExternalId(externalId));
        assertThat(visitResponses.size(), is(1));
        assertThat(visitResponses.get(0).getExternalId(), is(externalId));
        assertThat(visitResponses.get(0).getAppointmentDueDate(), is(dueDate));
    }

    @After
    public void tearDown() {
        allAppointmentCalendars.removeAll();
    }
}
