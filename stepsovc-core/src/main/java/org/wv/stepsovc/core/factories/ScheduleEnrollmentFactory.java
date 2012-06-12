package org.wv.stepsovc.core.factories;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.motechproject.model.Time;
import org.motechproject.scheduletracking.api.service.EnrollmentRequest;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.configuration.ScheduleNames;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.utils.DateUtils;

import java.text.ParseException;

@Component
public class ScheduleEnrollmentFactory {

    private Logger logger = Logger.getLogger(this.getClass());

    public EnrollmentRequest createEnrollmentRequest(Referral referral, String scheduleName) {
        try {
            LocalDate referenceDate = null;

            if (ScheduleNames.REFERRAL.getName().equals(scheduleName))
                referenceDate = DateUtils.prevLocalDate(referral.getServiceDate());
            else if (ScheduleNames.DEFAULTMENT.getName().equals(scheduleName))
                referenceDate = DateUtils.getLocalDate(referral.getServiceDate());

            return new EnrollmentRequest(referral.getOvcId(), scheduleName, new Time(0, 0),
                    referenceDate, null, referenceDate, null, null, referral.scheduleDataMap());
        } catch (ParseException e) {
            logger.error("ParseException while creating EnrollmentRequest , " + referral.getServiceDate());
        }
        return null;
    }
}
