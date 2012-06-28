package org.wv.stepsovc.core.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.motechproject.scheduletracking.api.service.EnrollmentRequest;
import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.fixtures.StepsovcCaseFixture;
import org.wv.stepsovc.core.mapper.ReferralMapper;
import org.wv.stepsovc.core.request.StepsovcCase;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class StepsovcScheduleServiceTest {

    private StepsovcScheduleService mockStepsovcScheduleService;
    @Mock
    private ScheduleTrackingService mockScheduleTrackingService;
    @Mock
    private StepsovcAlertService mockStepsovcAlertService;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        mockStepsovcScheduleService = new StepsovcScheduleService();
        ReflectionTestUtils.setField(mockStepsovcScheduleService, "scheduleTrackingService", mockScheduleTrackingService);
        ReflectionTestUtils.setField(mockStepsovcScheduleService, "stepsovcAlertService", mockStepsovcAlertService);
    }

    @Test
    public void shouldCreateAppointmentsAndSendNewReferralAlert() throws Exception {

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


}

