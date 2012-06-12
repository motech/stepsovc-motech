package org.wv.stepsovc.core.services;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.motechproject.appointments.api.service.contract.CreateVisitRequest;
import org.motechproject.scheduletracking.api.service.EnrollmentRequest;
import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.core.configuration.ScheduleNames;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.factories.FollowUpVisitFactory;
import org.wv.stepsovc.core.factories.ScheduleEnrollmentFactory;
import org.wv.stepsovc.core.fixtures.StepsovcCaseFixture;
import org.wv.stepsovc.core.mapper.ReferralMapper;
import org.wv.stepsovc.core.repository.AllAppointments;
import org.wv.stepsovc.core.request.StepsovcCase;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class StepsovcScheduleServiceTest {

    private StepsovcScheduleService stepsovcScheduleService;
    @Mock
    private ScheduleTrackingService mockScheduleTrackingService;
    @Mock
    private StepsovcAlertService mockStepsovcAlertService;
    @Mock
    private AllAppointments mockAllAppointments;
    @Mock
    private FollowUpVisitFactory mockFollowUpVisitFactory;
    private ScheduleEnrollmentFactory scheduleEnrollmentFactory;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        stepsovcScheduleService = new StepsovcScheduleService();
        scheduleEnrollmentFactory = new ScheduleEnrollmentFactory();
        ReflectionTestUtils.setField(stepsovcScheduleService, "scheduleTrackingService", mockScheduleTrackingService);
        ReflectionTestUtils.setField(stepsovcScheduleService, "stepsovcAlertService", mockStepsovcAlertService);
        ReflectionTestUtils.setField(stepsovcScheduleService, "allAppointments", mockAllAppointments);
        ReflectionTestUtils.setField(stepsovcScheduleService, "followUpVisitFactory", mockFollowUpVisitFactory);
        ReflectionTestUtils.setField(stepsovcScheduleService, "scheduleEnrollmentFactory", scheduleEnrollmentFactory);
    }

    @Test
    public void shouldCreateReferralSchedulesAndSendNewReferralAlert() throws Exception {

        String benCode = "BEN001";
        String serviceDate = "2012-05-30";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForReferral(benCode, serviceDate, "FAC001");
        stepsovcCase.setBeneficiary_code(benCode);
        Referral referral = new ReferralMapper().map(stepsovcCase);

        String ovcId = "someOvcId";
        referral.setOvcId(ovcId);

        stepsovcScheduleService.scheduleNewReferral(referral);
        ArgumentCaptor<EnrollmentRequest> enrollmentRequestArgumentCaptor = ArgumentCaptor.forClass(EnrollmentRequest.class);
        verify(mockScheduleTrackingService, times(2)).enroll(enrollmentRequestArgumentCaptor.capture());
        verify(mockStepsovcAlertService).sendInstantReferralAlertToFacility(referral);

        assertThat(enrollmentRequestArgumentCaptor.getAllValues().get(0).getScheduleName(), is(ScheduleNames.REFERRAL.getName()));
        assertThat(enrollmentRequestArgumentCaptor.getAllValues().get(1).getScheduleName(), is(ScheduleNames.DEFAULTMENT.getName()));

    }

    @Test
    public void shouldCreateAppointmentsIfFollowUpIsRequired() throws Exception {

        String benCode = "BEN001";
        String serviceDate = "2012-05-30";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForReferral(benCode, serviceDate, "FAC001");
        stepsovcCase.setBeneficiary_code(benCode);
        stepsovcCase.setFollowup_required("yes");
        stepsovcCase.setFollowup_date("2013-05-12");
        Referral referral = new ReferralMapper().map(stepsovcCase);

        String ovcId = "someOvcId";
        referral.setOvcId(ovcId);

        CreateVisitRequest visitRequest = new CreateVisitRequest();
        Map<String, Object> params = new HashMap<String, Object>();
        DateTime dateTime = new DateTime(2013, 5, 12, 0, 0);

        doReturn(visitRequest).when(mockFollowUpVisitFactory).createFollowUpVisitRequest(anyMap(), Matchers.<DateTime>anyObject());
        stepsovcScheduleService.scheduleNewReferral(referral);

        verify(mockAllAppointments).add(ovcId, visitRequest);
        verify(mockScheduleTrackingService, times(2)).enroll(Matchers.<EnrollmentRequest>anyObject());
        verify(mockStepsovcAlertService).sendInstantReferralAlertToFacility(referral);

    }

    @Test
    public void shouldRemoveAppointments() {
        String referralId = "referralId";
        stepsovcScheduleService.unscheduleFollowUpVisit(referralId);
        verify(mockAllAppointments).remove(referralId);
    }

}

