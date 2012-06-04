package org.wv.stepsovc.core.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.core.domain.Caregiver;
import org.wv.stepsovc.core.repository.AllCaregivers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class cgServiceTest {

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
    public void shouldAddCareGivers() {
        Caregiver caregiver = new Caregiver();
        caregiverService.addCareGiver(caregiver);
        verify(mockAllCaregivers).add(caregiver);
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


}
