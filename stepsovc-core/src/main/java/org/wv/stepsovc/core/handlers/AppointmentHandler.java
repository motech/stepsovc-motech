package org.wv.stepsovc.core.handlers;

import org.apache.log4j.Logger;
import org.motechproject.appointments.api.EventKeys;
import org.motechproject.model.MotechEvent;
import org.motechproject.server.event.annotations.MotechListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.services.StepsovcAlertService;

import java.util.Map;

@Component
public class AppointmentHandler {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private StepsovcAlertService stepsovcAlertService;

    @MotechListener(subjects = {EventKeys.APPOINTMENT_REMINDER_EVENT_SUBJECT})
    public void handleAlert(MotechEvent motechEvent) {
        try {
            Map<String, Object> parameters = motechEvent.getParameters();
            String externalId = (String) parameters.get(EventKeys.EXTERNAL_ID_KEY);
            logger.info("Handling Appointment - for External Id:" + externalId);
            stepsovcAlertService.sendFollowUpAlertToCaregiver(externalId);
        } catch (Exception e) {
            logger.debug("<Appointment Alert Exception>: Encountered error while sending alert: ", e);
            throw new EventHandlerException(motechEvent, e);
        }
    }
}
