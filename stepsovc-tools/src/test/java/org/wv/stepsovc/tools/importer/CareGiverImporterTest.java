package org.wv.stepsovc.tools.importer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.CareGiverInformation;
import org.wv.stepsovc.core.domain.Caregiver;
import org.wv.stepsovc.core.repository.AllCaregivers;
import org.wv.stepsovc.tools.dmis.DMISDataProcessor;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CareGiverImporterTest {


    CareGiverImporter careGiverImporter;
    @Mock
    private DMISDataProcessor mockDMISDataProcessor;
    @Mock
    private CommcareGateway mockCommcareGateway;
    @Mock
    private AllCaregivers mockAllCaregivers;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        careGiverImporter = new CareGiverImporter();
        ReflectionTestUtils.setField(careGiverImporter, "dmisDataProcessor", mockDMISDataProcessor);
        ReflectionTestUtils.setField(careGiverImporter, "commcareGateway", mockCommcareGateway);
        ReflectionTestUtils.setField(careGiverImporter, "allCaregivers", mockAllCaregivers);
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
    public void shouldPostImportedCareGivers() throws Exception {
        final String id1 = "123";
        final String id2 = "345";
        CareGiverInformation careGiverInfo1 = new CareGiverInformation();
        careGiverInfo1.setId(id1);
        CareGiverInformation careGiverInfo2 = new CareGiverInformation();
        careGiverInfo2.setId(id2);

        careGiverImporter.post(Arrays.asList(careGiverInfo1, careGiverInfo2));

        verify(mockCommcareGateway).registerUser(careGiverInfo1);
        verify(mockCommcareGateway).registerUser(careGiverInfo2);

        ArgumentCaptor<Caregiver> captor = ArgumentCaptor.forClass(Caregiver.class);

        verify(mockAllCaregivers, times(2)).add(captor.capture());

        assertThat(captor.getAllValues().get(0).getId(), is(id1));
        assertThat(captor.getAllValues().get(1).getId(), is(id2));
    }
}
