package org.wv.stepsovc.core.handlers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.motechproject.cmslite.api.model.ContentNotFoundException;
import org.motechproject.scheduletracking.api.events.MilestoneEvent;
import org.wv.stepsovc.core.configuration.ScheduleNames;
import org.wv.stepsovc.core.services.StepsovcAlertService;

import java.text.ParseException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
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
    public void shouldAlertFacilityOnReferral() throws ContentNotFoundException, ParseException {

        String externalId = "someExternalId";
        String windowName = "due";
        MilestoneEvent milestoneEvent = new MilestoneEvent(externalId, "Referral", null, windowName, null);

        referralScheduleHandler.handleAlert(milestoneEvent.toMotechEvent());
        verify(stepsovcAlertService).sendAggregatedReferralAlertToFacility(externalId, windowName);
    }

    @Test
    public void shouldThrowEventHandlerExceptionOnAnyFailure() {
        try {
            doThrow(new RuntimeException("some exception")).when(stepsovcAlertService).sendAggregatedReferralAlertToFacility(anyString(), anyString());
            MilestoneEvent milestoneEvent = new MilestoneEvent("", "Referral", null, "", null);
            referralScheduleHandler.handleAlert(milestoneEvent.toMotechEvent());
            Assert.fail("expected exception here");
        } catch (EventHandlerException ehe) {
        } catch (ContentNotFoundException e) {
        }
    }

    @Test
    public void shouldSendSMSToCareGiverForDefaultedReferral() throws ContentNotFoundException {
        String externalId = "ovcId";
        MilestoneEvent milestoneEvent = new MilestoneEvent(externalId, ScheduleNames.DEFAULTMENT.getName(), null, null, null);
        referralScheduleHandler.handleAlert(milestoneEvent.toMotechEvent());
        verify(stepsovcAlertService).sendInstantDefaultedAlertToCaregiver(externalId);

    }

}
