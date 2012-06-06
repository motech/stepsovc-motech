package org.wv.stepsovc.tools.importer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.vo.CaregiverInformation;
import org.wv.stepsovc.core.services.CaregiverService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CaregiverPhoneNumberImporterTest {

    CaregiverPhoneNumberAndFacilityCodeImporter caregiverPhoneNumberAndFacilityCodeImporter;

    @Mock
    private CaregiverService mockCaregiverService;


    private ByteArrayOutputStream errContent;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        caregiverPhoneNumberAndFacilityCodeImporter = new CaregiverPhoneNumberAndFacilityCodeImporter();
        ReflectionTestUtils.setField(caregiverPhoneNumberAndFacilityCodeImporter, "caregiverService", mockCaregiverService);
        errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
    }


    @Test
    public void shouldImportCareGiverPhoneNumbers() {
        String code1 = "123";
        String code2 = "345";
        String ph1 = "11111";
        String ph2 = "2222";

        CaregiverInformation careGiverInfo1 = new CaregiverInformation();
        careGiverInfo1.setCaregiverCode(code1);
        careGiverInfo1.setPhoneNumber(ph1);
        CaregiverInformation careGiverInfo2 = new CaregiverInformation();
        careGiverInfo2.setCaregiverCode(code2);
        careGiverInfo2.setPhoneNumber(ph2);
        List<CaregiverInformation> caregiverList = Arrays.asList(careGiverInfo1, careGiverInfo2);

        caregiverPhoneNumberAndFacilityCodeImporter.importCaregiverPhoneNumbers(caregiverList);

        for (CaregiverInformation careGiverInformation : caregiverList) {
            verify(mockCaregiverService).updateCaregiverPhoneNumberAndFacilityCode(careGiverInformation.getCaregiverCode(), careGiverInformation.getPhoneNumber(), careGiverInformation.getFacilityCode());
        }


    }

    @Test
    public void shouldDisplayErrorWhenInvalidCaregiverCodeIsImported() {
        String code1 = "123";
        String code2 = "345";
        String ph1 = "11111";
        String ph2 = "2222";
        String fc1 = "fac1";
        String fc2 = "fac2";

        CaregiverInformation careGiverInfo1 = new CaregiverInformation();
        careGiverInfo1.setCaregiverCode(code1);
        careGiverInfo1.setPhoneNumber(ph1);
        careGiverInfo1.setFacilityCode(fc1);
        CaregiverInformation careGiverInfo2 = new CaregiverInformation();
        careGiverInfo2.setCaregiverCode(code2);
        careGiverInfo2.setPhoneNumber(ph2);
        careGiverInfo2.setFacilityCode(fc2);
        List<CaregiverInformation> caregiverList = Arrays.asList(careGiverInfo1, careGiverInfo2);

        when(mockCaregiverService.updateCaregiverPhoneNumberAndFacilityCode(code1, ph1, fc1)).thenReturn(false);
        when(mockCaregiverService.updateCaregiverPhoneNumberAndFacilityCode(code2, ph2, fc2)).thenReturn(true);
        caregiverPhoneNumberAndFacilityCodeImporter.importCaregiverPhoneNumbers(caregiverList);
        assertEquals(code1 + " - is  invalid . This  record is  not  imported !!", errContent.toString().trim());


    }


    @After
    public void cleanUpStreams() {
        System.setErr(null);
    }

}
