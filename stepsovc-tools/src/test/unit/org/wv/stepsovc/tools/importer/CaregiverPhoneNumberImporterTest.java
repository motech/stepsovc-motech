package org.wv.stepsovc.tools.importer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.vo.CareGiverInformation;
import org.wv.stepsovc.core.services.CaregiverService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CaregiverPhoneNumberImporterTest {

    CaregiverPhoneNumberAndFacilityCodeImporter caregiverPhoneNumberAndFacilityCodeImporter;

    @Mock
    private CaregiverService mockCaregiverService;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        caregiverPhoneNumberAndFacilityCodeImporter = new CaregiverPhoneNumberAndFacilityCodeImporter();
        ReflectionTestUtils.setField(caregiverPhoneNumberAndFacilityCodeImporter, "caregiverService", mockCaregiverService);
    }

    @Test
    public void shouldImportCareGiverPhoneNumbers() {
        String code1 = "123";
        String code2 = "345";
        String ph1 = "11111";
        String ph2 = "2222";

        CareGiverInformation careGiverInfo1 = new CareGiverInformation();
        careGiverInfo1.setCaregiverCode(code1);
        careGiverInfo1.setPhoneNumber(ph1);
        CareGiverInformation careGiverInfo2 = new CareGiverInformation();
        careGiverInfo2.setCaregiverCode(code2);
        careGiverInfo2.setPhoneNumber(ph2);
        List<CareGiverInformation> caregiverList = Arrays.asList(careGiverInfo1, careGiverInfo2);

        caregiverPhoneNumberAndFacilityCodeImporter.importCaregiverPhoneNumbers(caregiverList);

        for (CareGiverInformation careGiverInformation : caregiverList) {
            verify(mockCaregiverService).updateCaregiverPhoneNumberAndFacilityCode(careGiverInformation.getCaregiverCode(), careGiverInformation.getPhoneNumber(), careGiverInformation.getFacilityCode());
        }


    }
}
