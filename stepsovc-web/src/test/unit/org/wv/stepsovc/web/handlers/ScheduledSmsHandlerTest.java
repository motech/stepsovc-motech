package org.wv.stepsovc.web.handlers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.motechproject.scheduler.domain.MotechEvent;
import org.motechproject.sms.api.constants.EventDataKeys;
import org.wv.stepsovc.core.services.StepsovcAlertService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ScheduledSmsHandlerTest {
    ScheduledSmsHandler scheduledSmsHandler;

    @Mock
    private StepsovcAlertService stepsovcAlertService;

    @Before
    public void setUp() {
        initMocks(this);
        scheduledSmsHandler = new ScheduledSmsHandler(stepsovcAlertService);
    }

    @Test
    public void shouldSendFollowUpAlertToCaregiver() {
        String externalId = "ovcId";
        Map<String, Object> params = new HashMap<String, Object>();
        String phoneNumber1 = "someNumber1";
        String phoneNumber2 = "someNumber2";
        List<String> recipients = Arrays.asList(phoneNumber1, phoneNumber2);
        params.put(EventDataKeys.RECIPIENTS, recipients);
        String message = "someMessage";
        params.put(EventDataKeys.MESSAGE, message);

        scheduledSmsHandler.handleScheduledSms(new MotechEvent("", params));
        verify(stepsovcAlertService).sendSMSToAll(recipients, message);
    }
}
