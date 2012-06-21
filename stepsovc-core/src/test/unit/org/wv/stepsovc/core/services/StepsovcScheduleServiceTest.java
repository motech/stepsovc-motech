package org.wv.stepsovc.core.services;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.motechproject.appointments.api.service.contract.CreateVisitRequest;
import org.motechproject.scheduletracking.api.service.EnrollmentRequest;
import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.factories.FollowUpVisitFactory;
import org.wv.stepsovc.core.fixtures.StepsovcCaseFixture;
import org.wv.stepsovc.core.mapper.ReferralMapper;
import org.wv.stepsovc.core.repository.AllAppointments;
import org.wv.stepsovc.core.request.StepsovcCase;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class StepsovcScheduleServiceTest {

    private StepsovcScheduleService mockStepsovcScheduleService;
    @Mock
    private ScheduleTrackingService mockScheduleTrackingService;
    @Mock
    private StepsovcAlertService mockStepsovcAlertService;
    @Mock
    private AllAppointments mockAllAppointments;
    @Mock
    private FollowUpVisitFactory mockFollowUpVisitFactory;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        mockStepsovcScheduleService = new StepsovcScheduleService();
        ReflectionTestUtils.setField(mockStepsovcScheduleService, "scheduleTrackingService", mockScheduleTrackingService);
        ReflectionTestUtils.setField(mockStepsovcScheduleService, "stepsovcAlertService", mockStepsovcAlertService);
        ReflectionTestUtils.setField(mockStepsovcScheduleService, "allAppointments", mockAllAppointments);
        ReflectionTestUtils.setField(mockStepsovcScheduleService, "followUpVisitFactory", mockFollowUpVisitFactory);
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

        mockStepsovcScheduleService.scheduleNewReferral(referral);

        verify(mockScheduleTrackingService).enroll(Matchers.<EnrollmentRequest>anyObject());
        verify(mockStepsovcAlertService).sendInstantReferralAlertToFacility(referral);

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
        mockStepsovcScheduleService.scheduleNewReferral(referral);

        verify(mockAllAppointments).add(ovcId, visitRequest);
        verify(mockScheduleTrackingService).enroll(Matchers.<EnrollmentRequest>anyObject());
        verify(mockStepsovcAlertService).sendInstantReferralAlertToFacility(referral);

    }


}

