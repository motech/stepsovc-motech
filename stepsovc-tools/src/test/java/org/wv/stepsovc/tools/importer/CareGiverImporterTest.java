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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    public void shouldPostImportedCareGivers() throws Exception {
        String id1 = "123";
        String id2 = "345";
        String name1 = "name1";
        String name2 = "name2";
        CareGiverInformation careGiverInfo1 = new CareGiverInformation();
        careGiverInfo1.setId(id1);
        careGiverInfo1.setName(name1);
        CareGiverInformation careGiverInfo2 = new CareGiverInformation();
        careGiverInfo2.setName(name2);
        careGiverInfo2.setId(id2);

        careGiverImporter.post(Arrays.asList(careGiverInfo1, careGiverInfo2));

        verify(mockCommcareGateway).registerUser(careGiverInfo1);
        verify(mockCommcareGateway).registerUser(careGiverInfo2);

        ArgumentCaptor<Caregiver> captor1 = ArgumentCaptor.forClass(Caregiver.class);

        verify(mockAllCaregivers, times(2)).add(captor1.capture());
        ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);

        verify(mockDMISDataProcessor, times(2)).decrypt(captor2.capture());

        assertThat(captor1.getAllValues().get(0).getId(), is(id1));
        assertThat(captor1.getAllValues().get(1).getId(), is(id2));
        assertThat(captor2.getAllValues().get(0), is(name1));
        assertThat(captor2.getAllValues().get(1), is(name2));
    }
}
