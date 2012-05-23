package org.wv.stepsovc.core.factories;

import org.joda.time.DateTime;
import org.motechproject.appointments.api.service.contract.CreateVisitRequest;
import org.motechproject.appointments.api.service.contract.ReminderConfiguration;
import org.wv.stepsovc.core.domain.Referral;

import java.util.Map;

public class VisitRequestFactory {

    static final int ONE_DAY_BEFORE = -1;
    static final int ONE_DAY_LATER = 1;

    public static CreateVisitRequest createVisitRequestForReferral(Map<String, Object> params, DateTime appointmentDate) {
        CreateVisitRequest createVisitRequest = new CreateVisitRequest().setAppointmentDueDate(appointmentDate).setVisitName(Referral.VISIT_NAME);
        createVisitRequest.setData(params);
        createVisitRequest.addAppointmentReminderConfiguration(new ReminderConfiguration().setRemindFrom(switchSign(ONE_DAY_BEFORE)));
        createVisitRequest.addAppointmentReminderConfiguration(new ReminderConfiguration());
        createVisitRequest.addAppointmentReminderConfiguration(new ReminderConfiguration().setRemindFrom(switchSign(ONE_DAY_LATER)));
        return createVisitRequest;
    }

    private static int switchSign(int days) {
        return -days;
    }
}
