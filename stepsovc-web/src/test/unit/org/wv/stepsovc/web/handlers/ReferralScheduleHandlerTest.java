package org.wv.stepsovc.web.handlers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.motechproject.scheduletracking.api.events.MilestoneEvent;
import org.wv.stepsovc.core.configuration.ScheduleNames;
import org.wv.stepsovc.core.services.StepsovcAlertService;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ReferralScheduleHandlerTest {
    private ReferralScheduleHandler referralScheduleHandler;
    @Mock
    private StepsovcAlertService stepsovcAlertService;

    @Before
    public void setUp() {
        initMocks(this);
        referralScheduleHandler = new ReferralScheduleHandler(stepsovcAlertService);
    }

    @Test
    public void shouldAlertFacilityOnReferral() {

        String externalId = "someExternalId";
        String windowName = "due";
        MilestoneEvent milestoneEvent = new MilestoneEvent(externalId, "Referral", null, windowName, null);

        referralScheduleHandler.handleAlert(milestoneEvent.toMotechEvent());
        verify(stepsovcAlertService).sendAggregatedReferralAlertToFacility(externalId, windowName);
    }


    @Test
    public void shouldSendSMSToCareGiverForDefaultedReferral() {
        String externalId = "ovcId";
        MilestoneEvent milestoneEvent = new MilestoneEvent(externalId, ScheduleNames.DEFAULTMENT.getName(), null, null, null);
        referralScheduleHandler.handleAlert(milestoneEvent.toMotechEvent());
        verify(stepsovcAlertService).sendInstantDefaultedAlertToCaregiver(externalId);

    }

}
