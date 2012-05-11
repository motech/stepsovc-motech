package org.wv.stepsovc.web.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.web.domain.Facility;
import org.wv.stepsovc.web.domain.ServiceUnavailability;
import org.wv.stepsovc.web.repository.AllFacilities;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class FacilityServiceTest {

    private FacilityService facilityService;
    @Mock
    private AllFacilities mockAllFacilities;
    private String facilityId = "123";
    private String serviceDate = "2012-01-02";


    @Before
    public void setUp() {
        initMocks(this);
        facilityService = new FacilityService();
        ReflectionTestUtils.setField(facilityService, "allFacilities", mockAllFacilities);
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
    public void shouldGetNextAvailableDateForGivenFacilityAndDate() {
        String unavailableDate = "2012-01-01";
        Facility facility = getFacilityWithServiceUnavailability();

        doReturn(facility).when(mockAllFacilities).findFacilityByCode(facilityId);
        String availableDate = facilityService.getFacilityAvailability(facilityId, unavailableDate).getNextAvailableDate();
        assertThat(availableDate, is("2012-01-07"));
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
