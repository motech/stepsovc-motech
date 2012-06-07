package org.wv.stepsovc.core.mapper;

import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.domain.Facility;

@Component
public class FacilityMapper {

    public Facility map(Facility existingFacility, Facility newFacility) {
        existingFacility.setFacilityCode(newFacility.getFacilityCode());
        existingFacility.setConstituency(newFacility.getConstituency());
        existingFacility.setDistrict(newFacility.getDistrict());
        existingFacility.setFacilityName(newFacility.getFacilityName());
        existingFacility.setVillage(newFacility.getVillage());
        existingFacility.setPhoneNumber(newFacility.getPhoneNumbers());
        existingFacility.setWard(newFacility.getWard());
        return existingFacility;
    }
}
