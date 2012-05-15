package org.wv.stepsovc.web.handlers;

import org.motechproject.appointments.api.EventKeys;
import org.motechproject.model.MotechEvent;
import org.motechproject.server.event.annotations.MotechListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReferralScheduleHandler {

    private Logger logger = LoggerFactory.getLogger(ReferralScheduleHandler.class);

    @MotechListener(subjects = {EventKeys.APPOINTMENT_REMINDER_EVENT_SUBJECT})
    public void handleAlert(MotechEvent motechEvent) {
        try {
            logger.info("Handling referral schedule "+motechEvent.getSubject());
        } catch (Exception e) {
            logger.error("<Appointment Alert Exception>: Encountered error while sending alert: ", e);
            //throw new EventHandlerException(motechEvent, e);
        }
    }
}
