package org.wv.stepsovc.tools.importer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.BeneficiaryInformation;
import org.wv.stepsovc.tools.dmis.DMISDataProcessor;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class BeneficiaryImporterTest {

    BeneficiaryImporter beneficiaryImporter;

    @Mock
    private CommcareGateway mockCommcareGateway;
    @Mock
    private DMISDataProcessor mockDmisDataProcessor;

    @Before
    public void setup() {
        initMocks(this);
        beneficiaryImporter = new BeneficiaryImporter();
        ReflectionTestUtils.setField(beneficiaryImporter, "commcareGateway", mockCommcareGateway);
        ReflectionTestUtils.setField(beneficiaryImporter, "dmisDataProcessor", mockDmisDataProcessor);

    }

    @Test
    public void shouldImportBeneficiaries() {
        String someBenName1 = "someBenName1";
        String someBenName2 = "someBenName2";
        String someCGName1 = "someCGName1";
        String someCGName2 = "someCGName2";

        BeneficiaryInformation beneficiaryInformation1 = new BeneficiaryInformation();
        BeneficiaryInformation beneficiaryInformation2 = new BeneficiaryInformation();
        beneficiaryInformation1.setBeneficiaryCode("someBenCode1");
        beneficiaryInformation1.setBeneficiaryId("someBenId1");
        beneficiaryInformation1.setReceivingOrganization("someReceivingOrganization1");
        beneficiaryInformation1.setBeneficiaryDob("someDOB1");
        beneficiaryInformation1.setBeneficiaryName(someBenName1);
        beneficiaryInformation1.setBeneficiarySex("F");
        beneficiaryInformation1.setCareGiverCode("someCGCode1");
        beneficiaryInformation1.setCareGiverName(someCGName1);

        beneficiaryInformation2.setBeneficiaryCode("someBenCode2");
        beneficiaryInformation2.setBeneficiaryId("someBenId2");
        beneficiaryInformation2.setReceivingOrganization("someReceivingOrganization2");
        beneficiaryInformation2.setBeneficiaryDob("someDOB2");
        beneficiaryInformation2.setBeneficiaryName(someBenName2);
        beneficiaryInformation2.setBeneficiarySex("M");
        beneficiaryInformation2.setCareGiverCode("someCGCode2");
        beneficiaryInformation2.setCareGiverName(someCGName2);

        final List<BeneficiaryInformation> beneficiaryInformationList = Arrays.asList(beneficiaryInformation1, beneficiaryInformation2);
        beneficiaryImporter.post(beneficiaryInformationList);
        verify(mockCommcareGateway).createCase(beneficiaryInformation1);
        verify(mockCommcareGateway).createCase(beneficiaryInformation2);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(mockDmisDataProcessor, times(4)).decrypt(captor.capture());

        assertThat(captor.getAllValues().get(0), is(someBenName1));
        assertThat(captor.getAllValues().get(1), is(someCGName1));
        assertThat(captor.getAllValues().get(2), is(someBenName2));
        assertThat(captor.getAllValues().get(3), is(someCGName2));

    }


}
    
    

