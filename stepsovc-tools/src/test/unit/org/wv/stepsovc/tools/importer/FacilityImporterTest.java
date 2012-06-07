package org.wv.stepsovc.tools.importer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.FacilityInformation;
import org.wv.stepsovc.core.domain.Facility;
import org.wv.stepsovc.core.mapper.FacilityMapper;
import org.wv.stepsovc.core.services.FacilityService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class FacilityImporterTest {


    private FacilityImporter facilityImporter;
    @Mock
    private CommcareGateway mockCommcareGateway;
    @Mock
    private FacilityService mockFacilityService;

    private FacilityMapper mockFacilityMapper;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
        facilityImporter = new FacilityImporter();
        ReflectionTestUtils.setField(facilityImporter, "commcareGateway", mockCommcareGateway);
        ReflectionTestUtils.setField(facilityImporter, "facilityService", mockFacilityService);
        ReflectionTestUtils.setField(facilityImporter, "facilityMapper", new FacilityMapper());
    }

    @Test
    public void shouldImportFacility() {
        FacilityInformation facilityInformation = new FacilityInformation();
        facilityInformation.setId("1");
        facilityInformation.setFacilityCode("code1");
        facilityInformation.setFacilityName("name1");
        facilityInformation.setMandatoryPhoneNumber("9999999");
        facilityInformation.setOptionalPhoneNumber1("7777777");
        List<FacilityInformation> facilityList = Arrays.asList(facilityInformation);
        ArgumentCaptor<Facility> facilityArgumentCaptor = ArgumentCaptor.forClass(Facility.class);
        facilityImporter.importFacilities(facilityList);
        verify(mockCommcareGateway).registerFacility(facilityInformation);
        verify(mockFacilityService).addFacility(facilityArgumentCaptor.capture());
        assertThat(facilityArgumentCaptor.getValue().getFacilityCode(), is(facilityInformation.getFacilityCode()));
        assertThat(facilityArgumentCaptor.getValue().getFacilityName(), is(facilityInformation.getFacilityName()));
        assertThat(facilityArgumentCaptor.getValue().getId(), is(facilityInformation.getId()));
        assertThat(facilityArgumentCaptor.getValue().getPhoneNumbers(), is(facilityInformation.getPhoneNumbers()));
    }
}
