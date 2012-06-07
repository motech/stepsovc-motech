package org.wv.stepsovc.core.mapper;


import org.junit.Test;
import org.wv.stepsovc.core.domain.Facility;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class FacilityMapperTest {

    FacilityMapper facilityMapper;

    @Test
    public void shouldMapPropertiesFromNewFacilityToExistingFacility() throws Exception {
        facilityMapper = new FacilityMapper();
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
