package org.wv.stepsovc.core.mapper;

import org.joda.time.LocalDate;
import org.motechproject.model.Time;
import org.motechproject.scheduletracking.api.service.EnrollmentRequest;
import org.wv.stepsovc.core.configuration.ScheduleNames;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.utils.DateUtils;

import java.text.ParseException;

public class ScheduleEnrollmentMapper {
    public EnrollmentRequest map(Referral referral) {
        try {
            LocalDate previousDate = DateUtils.prevLocalDate(referral.getServiceDate());
            return new EnrollmentRequest(referral.getOvcId(), ScheduleNames.REFERRAL.getName(), new Time(0, 0),
                    previousDate, null, previousDate, null, null, referral.appointmentDataMap());
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
