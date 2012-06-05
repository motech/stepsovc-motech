package org.wv.stepsovc.core.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.motechproject.cmslite.api.model.StringContent;
import org.motechproject.cmslite.api.service.CMSLiteService;
import org.motechproject.scheduletracking.api.service.EnrollmentRequest;
import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.motechproject.sms.api.service.SmsService;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.core.domain.Beneficiary;
import org.wv.stepsovc.core.domain.Facility;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.SmsTemplateKeys;
import org.wv.stepsovc.core.fixtures.StepsovcCaseFixture;
import org.wv.stepsovc.core.mapper.ReferralMapper;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.repository.AllFacilities;
import org.wv.stepsovc.core.request.StepsovcCase;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ReferralAlertServiceTest {

    private ReferralAlertService referralAlertService;
    @Mock
    private SmsService mockSmsService;
    @Mock
    private AllFacilities mockAllFacilities;
    @Mock
    private AllBeneficiaries mockAllBeneficiaries;
    @Mock
    private CMSLiteService mockCmsLiteService;
    @Mock
    private ScheduleTrackingService mockScheduleTrackingService;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        referralAlertService = new ReferralAlertService();
        ReflectionTestUtils.setField(referralAlertService, "smsService", mockSmsService);
        ReflectionTestUtils.setField(referralAlertService, "allFacilities", mockAllFacilities);
        ReflectionTestUtils.setField(referralAlertService, "allBeneficiaries", mockAllBeneficiaries);
        ReflectionTestUtils.setField(referralAlertService, "cmsLiteService", mockCmsLiteService);
        ReflectionTestUtils.setField(referralAlertService, "scheduleTrackingService", mockScheduleTrackingService);
    }

    @Test
    public void shouldCreateAppointmentsAndSendInstantSMSForNewReferralAlert() throws Exception {

        String benCode = "BEN001";
        String serviceDate = "2012-05-30";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForReferral(benCode, serviceDate, "FAC001");
        stepsovcCase.setBeneficiary_code(benCode);
        Referral referral = new ReferralMapper().map(stepsovcCase);

        String ovcId = "someOvcId";
        referral.setOvcId(ovcId);
        Facility facility = new Facility();
        Beneficiary beneficiary = new Beneficiary();
        List<String> phoneNumbers = Arrays.asList("0123456789", "1234555456");
        facility.setPhoneNumber(phoneNumbers);
        facility.setFacilityName("FAC001");
        beneficiary.setName("Ben Name");
        beneficiary.setCode(benCode);

        doReturn(facility).when(mockAllFacilities).findFacilityByCode(referral.getFacilityCode());
        doReturn(beneficiary).when(mockAllBeneficiaries).findBeneficiaryByCode(benCode);

        StringContent template = new StringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.IMPENDING_REFERRAL, "%s (%s) will be coming to your facility %s on %s. Please make the necessary arrangements.");
        doReturn(template).when(mockCmsLiteService).getStringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.IMPENDING_REFERRAL);

        referralAlertService.newReferralAlert(referral);

        verify(mockScheduleTrackingService).enroll(Matchers.<EnrollmentRequest>anyObject());
        verify(mockSmsService).sendSMS(phoneNumbers, "Ben Name (BEN001) will be coming to your facility FAC001 on 2012-05-30. Please make the necessary arrangements.");

    }


}

