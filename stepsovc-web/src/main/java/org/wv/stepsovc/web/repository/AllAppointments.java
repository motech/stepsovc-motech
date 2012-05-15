package org.wv.stepsovc.web.repository;

import org.joda.time.DateTime;
import org.motechproject.appointments.api.service.AppointmentService;
import org.motechproject.appointments.api.service.contract.CreateVisitRequest;
import org.motechproject.appointments.api.service.contract.ReminderConfiguration;
import org.motechproject.appointments.api.service.contract.VisitResponse;
import org.motechproject.appointments.api.service.contract.VisitsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.wv.stepsovc.web.domain.Referral;

import java.util.List;
import java.util.Map;

@Repository
public class AllAppointments {

    @Autowired
    AppointmentService appointmentService;

    static final int ONE_DAY_BEFORE = -1;
    static final int ONE_DAY_LATER = 1;

    public void scheduleNewReferral(String externalId, Map<String, Object> params, DateTime appointmentDate) {

        CreateVisitRequest createVisitRequest = new CreateVisitRequest().setAppointmentDueDate(appointmentDate).setVisitName(Referral.VISIT_NAME);
        createVisitRequest.setData(params);
        createVisitRequest.addAppointmentReminderConfiguration(new ReminderConfiguration().setRemindFrom(switchSign(ONE_DAY_BEFORE)));
        createVisitRequest.addAppointmentReminderConfiguration(new ReminderConfiguration());
        createVisitRequest.addAppointmentReminderConfiguration(new ReminderConfiguration().setRemindFrom(switchSign(ONE_DAY_LATER)));
        appointmentService.addVisit(externalId, createVisitRequest);
    }

    private int switchSign(int days) {
        return -days;
    }

    public List<VisitResponse> find(VisitsQuery visitsQuery) {
        return appointmentService.search(visitsQuery);
    }
}
