package org.wv.stepsovc.core.handlers;

import org.apache.log4j.Logger;
import org.motechproject.model.MotechEvent;
import org.motechproject.scheduletracking.api.events.MilestoneEvent;
import org.motechproject.scheduletracking.api.events.constants.EventSubjects;
import org.motechproject.server.event.annotations.MotechListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.services.StepsovcAlertService;

@Component
public class ReferralScheduleHandler {

    private Logger logger = Logger.getLogger(this.getClass());
    private StepsovcAlertService stepsovcAlertService;

    @Autowired
    public ReferralScheduleHandler(StepsovcAlertService stepsovcAlertService) {
        this.stepsovcAlertService = stepsovcAlertService;
    }

    @MotechListener(subjects = {EventSubjects.MILESTONE_ALERT})
    public void handleAlert(MotechEvent motechEvent) {
        MilestoneEvent milestoneEvent = new MilestoneEvent(motechEvent);
        logger.info("Handling Milestone alert " + milestoneEvent.getWindowName() + " - for External Id:" + milestoneEvent.getExternalId());
        try {
            stepsovcAlertService.sendAggregatedReferralAlertToFacility(milestoneEvent.getExternalId(), milestoneEvent.getWindowName());
        } catch (Exception e) {
            logger.error("<Milestone Alert Exception>: Encountered error while sending alert: ", e);
            throw new EventHandlerException(motechEvent, e);
        }

    }

    @MotechListener(subjects = {EventSubjects.DEFAULTMENT_CAPTURE})
    public void handleDefaultmentAlert(MotechEvent motechEvent) {
        MilestoneEvent milestoneEvent = new MilestoneEvent(motechEvent);
        logger.info("Handling Defaulted Schedule - for External Id:" + milestoneEvent.getExternalId());

        try {
            stepsovcAlertService.sendInstantDefaultedAlertToCaregiver(milestoneEvent.getExternalId());
        } catch (Exception e) {
            logger.error("<Milestone Alert Exception>: Encountered error while sending alert: ", e);
            throw new EventHandlerException(motechEvent, e);
        }
    }
}
