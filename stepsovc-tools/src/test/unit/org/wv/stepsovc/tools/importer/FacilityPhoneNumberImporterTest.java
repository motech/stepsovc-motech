package org.wv.stepsovc.tools.importer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.vo.FacilityInformation;
import org.wv.stepsovc.core.services.FacilityService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class FacilityPhoneNumberImporterTest {
    @Mock
    FacilityService mockFacilityService;
    FacilityPhoneNumberImporter facilityPhoneNumberImporter;
    List<FacilityInformation> facilityInformationList;


    private ByteArrayOutputStream errContent;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        facilityPhoneNumberImporter = new FacilityPhoneNumberImporter();
        ReflectionTestUtils.setField(facilityPhoneNumberImporter, "facilityService", mockFacilityService);
        List<String> phoneNumbers = Arrays.asList("1", "2", "3", "4");
        FacilityInformation facilityOne = getFacilityInformation("FAC001", phoneNumbers);
        FacilityInformation facilityTwo = getFacilityInformation("FAC002", phoneNumbers);
        facilityInformationList = Arrays.asList(facilityOne, facilityTwo);

        errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

    }

    @Test
    public void shouldUpdateFacilityPhoneNumbers() {
        facilityPhoneNumberImporter.updateFacilityPhoneNumber(facilityInformationList);
        for (FacilityInformation facilityInformation : facilityInformationList) {
            verify(mockFacilityService).updateFacilityPhoneNumber(facilityInformation.getFacilityCode(), facilityInformation.getPhoneNumbers());
        }

    }

    @Test
    public void shouldDisplayErrorWhenInvalidFacilityCodeIsEntered() {
        when(mockFacilityService.updateFacilityPhoneNumber(eq("FAC001"), anyList())).thenReturn(false);
        when(mockFacilityService.updateFacilityPhoneNumber(eq("FAC002"), anyList())).thenReturn(true);
        facilityPhoneNumberImporter.updateFacilityPhoneNumber(facilityInformationList);
        assertEquals("FAC001 - is  invalid . This  record is not  imported !!", errContent.toString().trim());
    }


    private FacilityInformation getFacilityInformation(String code, List<String> phoneNumbers) {
        FacilityInformation facilityInformation = new FacilityInformation();
        facilityInformation.setFacilityCode(code);
        facilityInformation.setPhoneNumbers(phoneNumbers);
        return facilityInformation;
    }
}