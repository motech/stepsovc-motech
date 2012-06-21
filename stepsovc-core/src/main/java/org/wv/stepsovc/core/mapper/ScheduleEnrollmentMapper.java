package org.wv.stepsovc.core.mapper;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.motechproject.model.Time;
import org.motechproject.scheduletracking.api.service.EnrollmentRequest;
import org.wv.stepsovc.core.configuration.ScheduleNames;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.utils.DateUtils;

import java.text.ParseException;

public class ScheduleEnrollmentMapper {

    private Logger logger = Logger.getLogger(this.getClass());

    public EnrollmentRequest map(Referral referral) {
        try {
            LocalDate previousDate = DateUtils.prevLocalDate(referral.getServiceDate());
            return new EnrollmentRequest(referral.getOvcId(), ScheduleNames.REFERRAL.getName(), new Time(0, 0),
                    previousDate, null, previousDate, null, null, referral.scheduleDataMap());
        } catch (ParseException e) {
            logger.error("ParseException while creating EnrollmentRequest , " + referral.getServiceDate());
        }
        return null;
    }
}
