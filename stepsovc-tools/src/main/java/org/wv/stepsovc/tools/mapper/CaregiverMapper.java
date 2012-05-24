package org.wv.stepsovc.tools.mapper;

import org.wv.stepsovc.commcare.vo.CareGiverInformation;
import org.wv.stepsovc.core.domain.Caregiver;

public class CaregiverMapper {

    public Caregiver map(CareGiverInformation careGiverInformation) {
        Caregiver caregiver = new Caregiver();
        caregiver.setId(careGiverInformation.getCaregiverId());
        caregiver.setCode(careGiverInformation.getCaregiverCode());
        caregiver.setFirstName(careGiverInformation.getFirstName());
        caregiver.setMiddleName(careGiverInformation.getMiddleName());
        caregiver.setLastName(careGiverInformation.getLastName());
        caregiver.setGender(careGiverInformation.getGender());
        caregiver.setPhoneNumber(careGiverInformation.getPhoneNumber());
        return caregiver;
    }
}
