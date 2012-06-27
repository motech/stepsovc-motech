package org.wv.stepsovc.core.handlers;

import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.motechproject.aggregator.inbound.EventAggregationGateway;
import org.motechproject.cmslite.api.model.ContentNotFoundException;
import org.motechproject.cmslite.api.model.StringContent;
import org.motechproject.cmslite.api.service.CMSLiteService;
import org.motechproject.model.MotechEvent;
import org.motechproject.scheduletracking.api.events.DefaultmentCaptureEvent;
import org.motechproject.scheduletracking.api.events.MilestoneEvent;
import org.motechproject.sms.api.service.SmsService;
import org.wv.stepsovc.core.aggregator.SMSMessage;
import org.wv.stepsovc.core.domain.*;
import org.wv.stepsovc.core.mapper.ReferralMapper;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.repository.AllCaregivers;
import org.wv.stepsovc.core.repository.AllFacilities;
import org.wv.stepsovc.core.repository.AllReferrals;
import org.wv.stepsovc.core.utils.DateUtils;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.wv.stepsovc.core.domain.SmsTemplateKeys.REFERRAL_ALERT_WITH_SERVICE;
import static org.wv.stepsovc.core.fixtures.StepsovcCaseFixture.createCaseForReferral;

public class ReferralScheduleHandlerTest {
    @Mock
    private AllReferrals mockAllReferrals;
    @Mock
    private AllFacilities mockAllFacilities;
    @Mock
    private CMSLiteService mockCmsLiteService;
    @Mock
    private AllBeneficiaries mockAllBeneficiaries;
    @Mock
    private EventAggregationGateway<SMSMessage> mockEventAggregationGateway;
    @Mock
    private SmsService smsService;
    @Mock
    private AllCaregivers allCaregivers;

    private Referral referral;
    private Facility facility;
    private Beneficiary beneficiary;
    private ReferralScheduleHandler referralScheduleHandler;

    @Before
    public void setUp() {
        initMocks(this);
        referralScheduleHandler = new ReferralScheduleHandler(mockEventAggregationGateway, mockCmsLiteService,
                mockAllReferrals, mockAllFacilities, mockAllBeneficiaries, allCaregivers, smsService);
        referral = new Referral();
        facility = new Facility();
        beneficiary = new Beneficiary();
        referral.setBeneficiaryCode("someBenCode");
        referral.setServiceDate("2013-12-12");
        referral.setFacilityCode("FAC001");
        beneficiary.setName("Smith");
        beneficiary.setCode("BEN001");
    }

    @Test
    public void shouldSendSmsToAllFacilityNumbers() throws ContentNotFoundException, ParseException {

        String facilityCode = "code";
        String bencode = "bencode";
        List<String> phoneNumbers = Arrays.asList("989898089", "8980890231");
        referral = new ReferralMapper().map(createCaseForReferral(bencode, "2012-12-3", facilityCode));
        facility.setPhoneNumber(phoneNumbers).setFacilityCode(facilityCode);
        beneficiary.setCode(bencode).setName("test").setCaregiverCode("caregivercode");

        String externalId = "someID";
        when(mockAllReferrals.findActiveByOvcId(externalId)).thenReturn(referral);
        when(mockAllFacilities.findFacilityByCode(facilityCode)).thenReturn(facility);
        when(mockAllBeneficiaries.findBeneficiaryByCode(bencode)).thenReturn(beneficiary);

        StringContent templateString = new StringContent(null, null, "%s (%s) Services (%s)");
        when(mockCmsLiteService.getStringContent(Locale.ENGLISH.getLanguage(), REFERRAL_ALERT_WITH_SERVICE)).thenReturn(templateString);

        MilestoneEvent milestoneEvent = new MilestoneEvent(externalId, "Referral", null, "due", DateUtils.prevLocalDate(referral.getServiceDate()).toDateTime(new LocalTime(0, 0)));

        referralScheduleHandler.handleAlert(milestoneEvent.toMotechEvent());
        ArgumentCaptor<SMSMessage> smsCaptor = ArgumentCaptor.forClass(SMSMessage.class);
        verify(mockEventAggregationGateway, times(2)).dispatch(smsCaptor.capture());
        assertThat(smsCaptor.getAllValues().size(), is(2));
        assertThat(smsCaptor.getAllValues().get(0).phoneNumber(), isIn(phoneNumbers));
        assertThat(smsCaptor.getAllValues().get(1).phoneNumber(), isIn(phoneNumbers));
        assertThat(smsCaptor.getAllValues().get(0).content(), is("test (bencode) Services (001,003,004,006,007,008,009,010)"));
        assertThat(smsCaptor.getAllValues().get(1).content(), is("test (bencode) Services (001,003,004,006,007,008,009,010)"));
    }

    @Test
    public void shouldThrowEventHandlerExceptionOnAnyFailure() {

        when(mockAllReferrals.findActiveByOvcId(anyString())).thenThrow(new RuntimeException("some exception"));
        try {
            referralScheduleHandler.handleAlert(new MotechEvent(""));
            Assert.fail("expected exception here");
        } catch (EventHandlerException ehe) {
        }
    }

    @Test
    public void shouldSendSMSToCareGiverForDefaultedReferral() throws ContentNotFoundException {
        String externalId = "ovcId";
        String caregiverId = "caregiverId";
        String phoneNumber = "9812345678";
        String msg = "Smith (BEN001) has not fulfilled the referral to FAC001 due on 2013-12-12";
        StringContent template = new StringContent("", "", "%s (%s) has not fulfilled the referral to %s due on %s");
        referral.setCgId(caregiverId);
        Caregiver caregiver = new Caregiver();
        caregiver.setPhoneNumber(phoneNumber);

        doReturn(referral).when(mockAllReferrals).findActiveByOvcId(externalId);
        doReturn(beneficiary).when(mockAllBeneficiaries).findBeneficiaryByCode(referral.getBeneficiaryCode());
        doReturn(caregiver).when(allCaregivers).findCaregiverById(caregiverId);
        doReturn(template).when(mockCmsLiteService).getStringContent("en", SmsTemplateKeys.DEFAULTED_REFERRAL);
        referralScheduleHandler.handleDefaultmentAlert(new DefaultmentCaptureEvent("", "", externalId).toMotechEvent());
        verify(smsService).sendSMS(phoneNumber, msg);

    }

}
