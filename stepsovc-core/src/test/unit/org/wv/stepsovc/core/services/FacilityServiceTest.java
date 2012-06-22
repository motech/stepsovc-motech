package org.wv.stepsovc.core.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.core.domain.Facility;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.ServiceUnavailability;
import org.wv.stepsovc.core.fixtures.StepsovcCaseFixture;
import org.wv.stepsovc.core.mapper.FacilityMapper;
import org.wv.stepsovc.core.repository.AllFacilities;
import org.wv.stepsovc.core.request.StepsovcCase;
import org.wv.stepsovc.core.utils.DateUtils;
import org.wv.stepsovc.core.vo.FacilityAvailability;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class FacilityServiceTest {

    private FacilityService facilityService;
    @Mock
    private AllFacilities mockAllFacilities;
    private String facilityId = "123";
    private String serviceDate = "2012-01-02";
    @Mock
    private FacilityMapper mockFacilityMapper;
    @Mock
    private StepsovcAlertService mockAlertService;
    @Mock
    private ReferralService mockReferralService;


    @Before
    public void setUp() {
        initMocks(this);
        facilityService = new FacilityService();
        ReflectionTestUtils.setField(facilityService, "allFacilities", mockAllFacilities);
        ReflectionTestUtils.setField(facilityService, "facilityMapper", mockFacilityMapper);
        ReflectionTestUtils.setField(facilityService, "stepsovcAlertService", mockAlertService);
        ReflectionTestUtils.setField(facilityService, "referralService", mockReferralService);
    }

    @Test
    public void shouldReturnTrueIfFacilityIsNotPresent() {
        doReturn(null).when(mockAllFacilities).findFacilityByCode(facilityId);
        boolean isAvailable = facilityService.getFacilityAvailability(facilityId, serviceDate).isAvailable();
        assertTrue(isAvailable);
    }

    @Test
    public void shouldReturnFalseIfFacilityIsPresentWithGivenUnavailableDate() {
        Facility facility = getFacilityWithServiceUnavailability();

        doReturn(facility).when(mockAllFacilities).findFacilityByCode(facilityId);
        assertFalse(facilityService.getFacilityAvailability(facilityId, "2012-01-01").isAvailable());
        assertFalse(facilityService.getFacilityAvailability(facilityId, "2012-01-02").isAvailable());
        assertFalse(facilityService.getFacilityAvailability(facilityId, "2012-01-03").isAvailable());
    }

    @Test
    public void shouldReturnTrueIfFacilityIsPresentWithoutGivenDate() {
        Facility facility = getFacilityWithServiceUnavailability();

        doReturn(facility).when(mockAllFacilities).findFacilityByCode(facilityId);
        assertTrue(facilityService.getFacilityAvailability(facilityId, "2011-12-30").isAvailable());
        assertTrue(facilityService.getFacilityAvailability(facilityId, "2012-01-08").isAvailable());
    }

    @Test
    public void shouldGetCorrectDatesForGivenFacilityAndDateWhenServiceIsUnavailable() {
        String unavailableDate = "2012-01-01";
        Facility facility = getFacilityWithServiceUnavailability();

        doReturn(facility).when(mockAllFacilities).findFacilityByCode(facilityId);
        FacilityAvailability facilityAvailability = facilityService.getFacilityAvailability(facilityId, unavailableDate);
        assertThat(facilityAvailability.getNextAvailableDate(), is("2012-01-07"));
        assertThat(facilityAvailability.getUnavailableFromDate(), is("2012-01-04"));
        assertThat(facilityAvailability.getUnavailableToDate(), is("2012-01-06"));
    }

    @Test
    public void shouldAddFacilityIfNotExists() {
        Facility facility = new Facility();
        String code = "code";
        facility.setFacilityCode(code);
        when(mockAllFacilities.findFacilityByCode(code)).thenReturn(null);
        facilityService.addFacility(facility);
        verify(mockAllFacilities).add(facility);
    }

    @Test
    public void shouldUpdateIfFacilityExists() {
        Facility facility = new Facility();
        String code = "code";
        facility.setFacilityCode(code);
        facility.setId("1234");
        List<String> phoneNumbers = Arrays.asList("1234", "2222");
        facility.setPhoneNumber(phoneNumbers);
        Facility existingFacility = new Facility();
        existingFacility.setId("222222");
        when(mockAllFacilities.findFacilityByCode(code)).thenReturn(existingFacility);
        when(mockFacilityMapper.map(existingFacility, facility)).thenReturn(facility);
        facilityService.addFacility(facility);
        verify(mockAllFacilities).update(facility);
    }

    @Test
    public void shouldInvokeAlertServiceToSendInstantMsgToCareGiverWhenFacilityIsUnavailable() throws ParseException {
        String facilityCode = "testCode";
        String unavailableDate = "2012-12-12";
        String nextAvailableDate = "2012-12-13";
        StepsovcCase facilityCase = StepsovcCaseFixture.createNewCaseWithFacilityCode(facilityCode, unavailableDate, unavailableDate);

        List<Referral> updatedReferrals = Arrays.asList(new Referral());
        doReturn(updatedReferrals).when(mockReferralService).updateReferralsServiceDate(facilityCode, facilityCase.getService_unavailable_from(),
                facilityCase.getService_unavailable_to(), DateUtils.nextDateStr(facilityCase.getService_unavailable_to()));
        facilityService.makeFacilityUnavailable(facilityCase);

        verify(mockAlertService).sendInstantServiceUnavailabilityMsgToCareGivers(updatedReferrals, facilityCode, unavailableDate, unavailableDate, nextAvailableDate);

    }

    @Test
    public void shouldUpdateFacilityPhoneNumbersWhenFacilityExists() {
        String code = "FAC001";
        List<String> phoneNumbers = Arrays.asList("1", "2", "3", "4");
        Facility facility = new Facility();
        facility.setFacilityCode(code);
        when(mockAllFacilities.findFacilityByCode(code)).thenReturn(facility);
        facilityService.updateFacilityPhoneNumber(code, phoneNumbers);
        ArgumentCaptor<Facility> argumentCaptor = ArgumentCaptor.forClass(Facility.class);
        verify(mockAllFacilities).update(argumentCaptor.capture());
        assertEquals(code, argumentCaptor.getValue().getFacilityCode());
        assertEquals(phoneNumbers, argumentCaptor.getValue().getPhoneNumbers());

    }

    @Test
    public void shouldReturnFalseWhenInvalidFacilityCodeIsUpdatedWithPhoneNumber() {
        String code = "FAC001";
        List<String> phoneNumbers = Arrays.asList("1", "2", "3", "4");
        when(mockAllFacilities.findFacilityByCode(code)).thenReturn(null);
        boolean returnValue = facilityService.updateFacilityPhoneNumber(code, phoneNumbers);
        verify(mockAllFacilities, never()).update((Facility) anyObject());
        assertFalse(returnValue);
    }


    private Facility getFacilityWithServiceUnavailability() {
        Facility facility = new Facility();
        ServiceUnavailability serviceUnavailability1 = new ServiceUnavailability("test", "2012-01-01", "2012-01-03");
        ServiceUnavailability serviceUnavailability2 = new ServiceUnavailability("test", "2012-01-04", "2012-01-06");
        ServiceUnavailability serviceUnavailability3 = new ServiceUnavailability("test", "2012-01-09", "2012-01-20");
        ServiceUnavailability serviceUnavailability4 = new ServiceUnavailability("test", "2012-02-01", "2012-02-03");
        List<ServiceUnavailability> serviceUnavailabilities = Arrays.asList(serviceUnavailability2, serviceUnavailability1, serviceUnavailability3, serviceUnavailability4);
        facility.setServiceUnavailabilities(serviceUnavailabilities);
        return facility;
    }


}
