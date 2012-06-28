package unit.org.wv.stepsovc.core.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.motechproject.aggregator.inbound.EventAggregationGateway;
import org.motechproject.cmslite.api.model.ContentNotFoundException;
import org.motechproject.cmslite.api.model.StringContent;
import org.motechproject.cmslite.api.service.CMSLiteService;
import org.motechproject.sms.api.service.SmsService;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.core.aggregator.SMSMessage;
import org.wv.stepsovc.core.domain.*;
import org.wv.stepsovc.core.mapper.ReferralMapper;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.repository.AllCaregivers;
import org.wv.stepsovc.core.repository.AllFacilities;
import org.wv.stepsovc.core.repository.AllReferrals;
import org.wv.stepsovc.core.request.StepsovcCase;
import org.wv.stepsovc.core.services.StepsovcAlertService;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.wv.stepsovc.core.domain.SmsTemplateKeys.REFERRAL_ALERT_WITH_SERVICE;
import static org.wv.stepsovc.core.fixtures.StepsovcCaseFixture.createCaseForReferral;

public class StepsovcAlertServiceTest {
    @Mock
    private AllReferrals allReferrals;
    @Mock
    private AllFacilities allFacilities;
    @Mock
    private CMSLiteService cmsLiteService;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private EventAggregationGateway<SMSMessage> eventAggregationGateway;
    @Mock
    private SmsService smsService;
    @Mock
    private AllCaregivers allCaregivers;

    private StepsovcAlertService stepsovcAlertService;
    private Referral referral;
    private Facility facility;
    private Beneficiary beneficiary;

    @Before
    public void setUp() {
        initMocks(this);
        stepsovcAlertService = new StepsovcAlertService();
        ReflectionTestUtils.setField(stepsovcAlertService, "allCaregivers", allCaregivers);
        ReflectionTestUtils.setField(stepsovcAlertService, "allBeneficiaries", allBeneficiaries);
        ReflectionTestUtils.setField(stepsovcAlertService, "allFacilities", allFacilities);
        ReflectionTestUtils.setField(stepsovcAlertService, "allReferrals", allReferrals);
        ReflectionTestUtils.setField(stepsovcAlertService, "cmsLiteService", cmsLiteService);
        ReflectionTestUtils.setField(stepsovcAlertService, "eventAggregationGateway", eventAggregationGateway);
        ReflectionTestUtils.setField(stepsovcAlertService, "smsService", smsService);
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
        when(allReferrals.findActiveByOvcId(externalId)).thenReturn(referral);
        when(allFacilities.findFacilityByCode(facilityCode)).thenReturn(facility);
        when(allBeneficiaries.findBeneficiaryByCode(bencode)).thenReturn(beneficiary);

        StringContent templateString = new StringContent(null, null, "%s (%s) Services (%s)");
        when(cmsLiteService.getStringContent(Locale.ENGLISH.getLanguage(), REFERRAL_ALERT_WITH_SERVICE)).thenReturn(templateString);

        stepsovcAlertService.sendAggregatedReferralAlertToFacility(externalId, "due");
        ArgumentCaptor<SMSMessage> smsCaptor = ArgumentCaptor.forClass(SMSMessage.class);
        verify(eventAggregationGateway, times(2)).dispatch(smsCaptor.capture());
        assertThat(smsCaptor.getAllValues().size(), is(2));
        assertThat(smsCaptor.getAllValues().get(0).phoneNumber(), isIn(phoneNumbers));
        assertThat(smsCaptor.getAllValues().get(1).phoneNumber(), isIn(phoneNumbers));
        assertThat(smsCaptor.getAllValues().get(0).content(), is("test (bencode) Services (001,003,004,006,007,008,009,010)"));
        assertThat(smsCaptor.getAllValues().get(1).content(), is("test (bencode) Services (001,003,004,006,007,008,009,010)"));
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

        doReturn(referral).when(allReferrals).findActiveByOvcId(externalId);
        doReturn(beneficiary).when(allBeneficiaries).findBeneficiaryByCode(referral.getBeneficiaryCode());
        doReturn(caregiver).when(allCaregivers).findCaregiverById(caregiverId);
        doReturn(template).when(cmsLiteService).getStringContent("en", SmsTemplateKeys.DEFAULTED_REFERRAL);
        stepsovcAlertService.sendInstantDefaultedAlertToCaregiver(externalId);
        verify(smsService).sendSMS(phoneNumber, msg);

    }

    @Test
    public void shouldSendInstantReferralAlertSMSToFacilityNumbers() throws ContentNotFoundException {

        String benCode = "BEN001";
        String serviceDate = "2012-05-30";
        StepsovcCase stepsovcCase = org.wv.stepsovc.core.fixtures.StepsovcCaseFixture.createCaseForReferral(benCode, serviceDate, "FAC001");
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

        doReturn(facility).when(allFacilities).findFacilityByCode(referral.getFacilityCode());
        doReturn(beneficiary).when(allBeneficiaries).findBeneficiaryByCode(benCode);

        StringContent template = new StringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.IMPENDING_REFERRAL, "%s (%s) will be coming to your facility %s on %s. Please make the necessary arrangements.");
        doReturn(template).when(cmsLiteService).getStringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.IMPENDING_REFERRAL);

        stepsovcAlertService.sendInstantReferralAlertToFacility(referral);

        verify(smsService).sendSMS(phoneNumbers, "Ben Name (BEN001) will be coming to your facility FAC001 on 2012-05-30. Please make the necessary arrangements.");

    }

}
