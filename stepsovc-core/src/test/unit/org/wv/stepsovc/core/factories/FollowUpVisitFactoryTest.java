package org.wv.stepsovc.core.factories;

import org.joda.time.DateTime;
import org.junit.Test;
import org.motechproject.appointments.api.service.contract.CreateVisitRequest;
import org.wv.stepsovc.core.configuration.VisitNames;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FollowUpVisitFactoryTest {
    private FollowUpVisitFactory followUpVisitFactory;

    @Test
    public void shouldCreateFollowUpVisitRequest() throws Exception {
        followUpVisitFactory = new FollowUpVisitFactory();
        Map<String, Object> params = new HashMap<String, Object>();
        DateTime dateTime = new DateTime(2012, 05, 12, 0, 0);
        CreateVisitRequest visitRequest = followUpVisitFactory.createFollowUpVisitRequest(params, dateTime);

        assertThat(visitRequest.getVisitName(), is(VisitNames.FOLLOW_UP));
        assertThat(visitRequest.getAppointmentDueDate(), is(dateTime));
        assertThat(visitRequest.getData(), is(params));
    }
}
