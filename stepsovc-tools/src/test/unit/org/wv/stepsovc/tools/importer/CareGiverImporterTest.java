package org.wv.stepsovc.tools.importer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.CaregiverInformation;
import org.wv.stepsovc.core.domain.Caregiver;
import org.wv.stepsovc.core.services.CaregiverService;
import org.wv.stepsovc.tools.dmis.DMISDataProcessor;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CareGiverImporterTest {

    CaregiverImporter careGiverImporter;
    @Mock
    private DMISDataProcessor mockDMISDataProcessor;
    @Mock
    private CommcareGateway mockCommcareGateway;
    @Mock
    private CaregiverService mockCaregiverService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        careGiverImporter = new CaregiverImporter();
        ReflectionTestUtils.setField(careGiverImporter, "dmisDataProcessor", mockDMISDataProcessor);
        ReflectionTestUtils.setField(careGiverImporter, "commcareGateway", mockCommcareGateway);
        ReflectionTestUtils.setField(careGiverImporter, "caregiverService", mockCaregiverService);
    }

    @Test
    public void shouldPostImportedCareGivers() throws Exception {
        String id1 = "123";
        String id2 = "345";
        String fname1 = "fname1";
        String mname1 = "mname1";
        String lname1 = "lname1";
        String fname2 = "fname2";
        String mname2 = "mname2";
        String lname2 = "lname2";
        CaregiverInformation careGiverInfo1 = new CaregiverInformation();
        careGiverInfo1.setCaregiverId(id1);
        careGiverInfo1.setFirstName(fname1);
        careGiverInfo1.setMiddleName(mname1);
        careGiverInfo1.setLastName(lname1);
        CaregiverInformation careGiverInfo2 = new CaregiverInformation();
        careGiverInfo2.setCaregiverId(id2);
        careGiverInfo2.setFirstName(fname2);
        careGiverInfo2.setLastName(lname2);
        careGiverInfo2.setMiddleName(mname2);

        careGiverImporter.post(Arrays.asList(careGiverInfo1, careGiverInfo2));

        verify(mockCommcareGateway).registerCaregiver(careGiverInfo1);
        verify(mockCommcareGateway).registerCaregiver(careGiverInfo2);

        ArgumentCaptor<Caregiver> captor1 = ArgumentCaptor.forClass(Caregiver.class);

        verify(mockCaregiverService, times(2)).addCareGiver(captor1.capture());
        ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);

        verify(mockDMISDataProcessor, times(6)).decrypt(captor2.capture());
        verify(mockCommcareGateway).createOrUpdateGroup(CommcareGateway.ALL_USERS_GROUP, new String[]{id1, id2});
        assertThat(captor1.getAllValues().get(0).getCgId(), is(id1));
        assertThat(captor1.getAllValues().get(1).getCgId(), is(id2));
        assertThat(captor2.getAllValues().get(0), is(fname1));
        assertThat(captor2.getAllValues().get(1), is(mname1));
        assertThat(captor2.getAllValues().get(2), is(lname1));
        assertThat(captor2.getAllValues().get(3), is(fname2));
        assertThat(captor2.getAllValues().get(4), is(mname2));
        assertThat(captor2.getAllValues().get(5), is(lname2));
    }
}
