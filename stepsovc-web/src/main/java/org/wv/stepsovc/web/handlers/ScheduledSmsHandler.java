package org.wv.stepsovc.web.handlers;

import org.apache.log4j.Logger;
import org.motechproject.scheduler.domain.MotechEvent;
import org.motechproject.server.event.annotations.MotechListener;
import org.motechproject.sms.api.constants.EventDataKeys;
import org.motechproject.sms.api.constants.EventSubjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.wv.stepsovc.core.services.StepsovcAlertService;

import java.util.List;

public class ScheduledSmsHandler {

    private Logger logger = Logger.getLogger(this.getClass());
    private StepsovcAlertService stepsovcAlertService;

    @Autowired
    public ScheduledSmsHandler(StepsovcAlertService stepsovcAlertService) {
        this.stepsovcAlertService = stepsovcAlertService;
    }

    @MotechListener(subjects = {EventSubjects.SEND_SMS})
    public void handleScheduledSms(MotechEvent motechEvent) {
        List<String> recipients = (List<String>) motechEvent.getParameters().get(EventDataKeys.RECIPIENTS);
        String message = (String) motechEvent.getParameters().get(EventDataKeys.MESSAGE);
        stepsovcAlertService.sendSMSToAll(recipients, message);
    }
}
