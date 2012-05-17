package org.wv.stepsovc.tools.importer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.tools.dmis.DMISDataProcessor;
import org.wv.stepsovc.vo.CareGiverInformation;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CareGiverImporterTest {


    CareGiverImporter careGiverImporter;
    @Mock
    private DMISDataProcessor mockDMISDataProcessor;
    @Mock
    private CommcareGateway mockCommcareGateway;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        careGiverImporter = new CareGiverImporter();
        ReflectionTestUtils.setField(careGiverImporter, "dmisDataProcessor", mockDMISDataProcessor);
        ReflectionTestUtils.setField(careGiverImporter, "commcareGateway", mockCommcareGateway);
    }

    @Test
    public void shouldInvokeDecrypterWhileTransform() {

        final String encryptedName1 = "encryptedName1";
        final String encryptedName2 = "encryptedName2";
        final String decryptedName1 = "decryptedName1";
        final String decryptedName2 = "decryptedName2";

        CareGiverInformation careGiverInformation1 = new CareGiverInformation();
        careGiverInformation1.setName("encryptedName1");

        CareGiverInformation careGiverInformation2 = new CareGiverInformation();
        careGiverInformation2.setName("encryptedName2");

        doReturn(decryptedName1).when(mockDMISDataProcessor).decrypt(encryptedName1);
        doReturn(decryptedName2).when(mockDMISDataProcessor).decrypt(encryptedName2);

        final List<CareGiverInformation> transformedCareGiverInfoList = careGiverImporter.transform(Arrays.asList(careGiverInformation1, careGiverInformation2));
        assertEquals(decryptedName1, transformedCareGiverInfoList.get(0).getName());
        assertEquals(decryptedName2, transformedCareGiverInfoList.get(1).getName());
    }

    @Test
    public void shouldInvokeCommcareGatewayToPostImportedData() throws Exception {
        final CareGiverInformation careGiver1 = new CareGiverInformation();
        final CareGiverInformation careGiver2 = new CareGiverInformation();
        careGiverImporter.post(Arrays.asList(careGiver1, careGiver2));
        verify(mockCommcareGateway).registerUser(careGiver1);
        verify(mockCommcareGateway).registerUser(careGiver2);
    }
}
