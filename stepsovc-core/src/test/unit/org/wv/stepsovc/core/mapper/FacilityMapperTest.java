package org.wv.stepsovc.core.mapper;


import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.wv.stepsovc.commcare.vo.FacilityInformation;
import org.wv.stepsovc.core.domain.Facility;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FacilityMapperTest {

    FacilityMapper facilityMapper;

    @Before
    public void setUp() throws Exception {
        facilityMapper = new FacilityMapper();
    }

    @Test
    public void shouldMapPropertiesFromNewFacilityToExistingFacility() throws Exception {
        Facility existingFacility = createFacility("111", "FAC001", "FacName1", "District1", "village1", "ward1", "constituency1", Arrays.asList("123", "345"));
        final List<String> newPhoneNumbers = Arrays.asList("123", "999", "444");
        Facility newFacility = createFacility("newId", "FAC002", "FacName2", "District2", "village2", "ward2", "constituency2", newPhoneNumbers);
        existingFacility = facilityMapper.map(existingFacility, newFacility);
        assertEquals("111", existingFacility.getId());
        assertEquals(newFacility.getFacilityName(), existingFacility.getFacilityName());
        assertEquals(newFacility.getFacilityCode(), existingFacility.getFacilityCode());
        assertEquals(newFacility.getDistrict(), existingFacility.getDistrict());
        assertEquals(newFacility.getVillage(), existingFacility.getVillage());
        assertEquals(newFacility.getWard(), existingFacility.getWard());
        assertEquals(newFacility.getConstituency(), existingFacility.getConstituency());
        assertEquals(newFacility.getPhoneNumbers(), existingFacility.getPhoneNumbers());
    }

    @Test
    public void shouldCopyFacilityInformationToFacility() {

        FacilityInformation facilityInformation = createFacilityInformation("111", "FAC001", "FacName1", "District1", "village1", "ward1", "constituency1");
        Facility facility = facilityMapper.mapFacilityInformationToFacility(facilityInformation);
        assertThat(facility.getFacilityCode(), is(facilityInformation.getFacilityCode()));
        assertThat(facility.getId(), is(facilityInformation.getId()));
        assertThat(facility.getFacilityName(), is(facilityInformation.getFacilityName()));
        assertThat(facility.getVillage(), is(facilityInformation.getVillage()));
        assertThat(facility.getDistrict(), is(facilityInformation.getDistrict()));
        assertThat(facility.getWard(), is(facilityInformation.getWard()));
        assertThat(facility.getConstituency(), is(facilityInformation.getConstituency()));
        assertThat(facility.getPhoneNumbers(), is(facilityInformation.getPhoneNumbers()));


    }

    private FacilityInformation createFacilityInformation(String id, String code, String name, String district, String village, String ward, String constituency) {
        FacilityInformation facilityInformation = new FacilityInformation();
        facilityInformation.setId(id);
        facilityInformation.setFacilityCode(code);
        facilityInformation.setFacilityName(name);
        facilityInformation.setDistrict(district);
        facilityInformation.setVillage(village);
        facilityInformation.setWard(ward);
        facilityInformation.setConstituency(constituency);
        facilityInformation.setMandatoryPhoneNumber("1111");
        facilityInformation.setOptionalPhoneNumber1("2222");
        facilityInformation.setOptionalPhoneNumber2("3333");
        facilityInformation.setOptionalPhoneNumber3("4444");
        facilityInformation.setCreationDate(DateTime.now().toString());
        return facilityInformation;

    }


    private Facility createFacility(String id, String code, String name, String district, String village, String ward, String constituency, List<String> phoneNumbers) {
        Facility facility = new Facility();
        facility.setId(id);
        facility.setFacilityCode(code);
        facility.setFacilityName(name);
        facility.setDistrict(district);
        facility.setVillage(village);
        facility.setWard(ward);
        facility.setConstituency(constituency);
        facility.setPhoneNumber(phoneNumbers);
        return facility;

    }
}
