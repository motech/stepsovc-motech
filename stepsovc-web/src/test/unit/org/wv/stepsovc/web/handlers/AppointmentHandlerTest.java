package org.wv.stepsovc.web.handlers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.motechproject.appointments.api.EventKeys;
import org.motechproject.scheduler.domain.MotechEvent;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.core.services.StepsovcAlertService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class AppointmentHandlerTest {

    AppointmentHandler appointmentHandler;

    @Mock
    private StepsovcAlertService stepsovcAlertService;

    @Before
    public void setUp() {
        initMocks(this);
        appointmentHandler = new AppointmentHandler();
        ReflectionTestUtils.setField(appointmentHandler, "stepsovcAlertService", stepsovcAlertService);
    }

    @Test
    public void shouldSendFollowUpAlertToCaregiver() {
        String externalId = "ovcId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(EventKeys.EXTERNAL_ID_KEY, externalId);
        appointmentHandler.handleAlert(new MotechEvent("", params));
        verify(stepsovcAlertService).sendFollowUpAlertToCaregiver(externalId);
    }
}
