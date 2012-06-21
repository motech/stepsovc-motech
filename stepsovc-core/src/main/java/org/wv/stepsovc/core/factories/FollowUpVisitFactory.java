package org.wv.stepsovc.core.factories;

import org.joda.time.DateTime;
import org.motechproject.appointments.api.service.contract.CreateVisitRequest;
import org.motechproject.appointments.api.service.contract.ReminderConfiguration;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.configuration.VisitNames;

import java.util.Map;

@Component
public class FollowUpVisitFactory {

    public CreateVisitRequest createFollowUpVisitRequest(Map<String, Object> params, DateTime appointmentDate) {
        CreateVisitRequest createVisitRequest = new CreateVisitRequest().setAppointmentDueDate(appointmentDate).setVisitName(VisitNames.FOLLOW_UP);
        createVisitRequest.setData(params);
        createVisitRequest.addAppointmentReminderConfiguration(new ReminderConfiguration());
        return createVisitRequest;
    }
}
