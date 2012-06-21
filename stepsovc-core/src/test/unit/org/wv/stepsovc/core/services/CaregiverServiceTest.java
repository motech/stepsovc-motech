package org.wv.stepsovc.core.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.core.domain.Caregiver;
import org.wv.stepsovc.core.repository.AllCaregivers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CaregiverServiceTest {

    CaregiverService caregiverService;

    @Mock
    AllCaregivers mockAllCaregivers;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        caregiverService = new CaregiverService();
        ReflectionTestUtils.setField(caregiverService, "allCaregivers", mockAllCaregivers);
    }

    @Test
    public void shouldAddNewCareGivers() {
        Caregiver caregiver = new Caregiver();
        caregiverService.addCareGiver(caregiver);
        verify(mockAllCaregivers).add(caregiver);
        verify(mockAllCaregivers, never()).update(caregiver);
    }


    @Test
    public void shouldUpdateCaregiversIfCareGiverCodeIsAlreadyPresent() {
        String careGiverCode = "someCode";
        Caregiver caregiver = new Caregiver();
        caregiver.setCode(careGiverCode);
        Caregiver existingCareGiver = new Caregiver();
        when(mockAllCaregivers.findCaregiverByCode(careGiverCode)).thenReturn(existingCareGiver);
        caregiverService.addCareGiver(caregiver);
        verify(mockAllCaregivers).update(caregiver);
        verify(mockAllCaregivers, never()).add(caregiver);
    }

    @Test
    public void shouldUpdatePhoneNumberAndFacilityForCaregiver() {
        Caregiver caregiver = new Caregiver();
        caregiver.setCode("code");
        String caregiverCode = "code";
        String phoneNumber = "1221222";
        String facilityCode = "FAC001";
        when(mockAllCaregivers.findCaregiverByCode(caregiverCode)).thenReturn(caregiver);
        caregiverService.updateCaregiverPhoneNumberAndFacilityCode(caregiverCode, phoneNumber, facilityCode);
        assertEquals(phoneNumber, caregiver.getPhoneNumber());
        assertEquals(facilityCode, caregiver.getFacilityCode());
        verify(mockAllCaregivers).update(caregiver);
    }

    @Test
    public void shouldReturnFalseWhenInvalidCaregiverIsGivenForUpdation() {
        String invalidCode = "invalidCode";
        when(mockAllCaregivers.findCaregiverByCode(invalidCode)).thenReturn(null);
        assertFalse(caregiverService.updateCaregiverPhoneNumberAndFacilityCode(invalidCode, "someNumber", "saomeFacility"));
    }

}
