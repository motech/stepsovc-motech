package org.wv.stepsovc.tools.mapper;

import org.wv.stepsovc.commcare.vo.cgInformation;
import org.wv.stepsovc.core.domain.Caregiver;

public class CaregiverMapper {

    public Caregiver map(cgInformation careGiverInformation) {
        Caregiver caregiver = new Caregiver();
        caregiver.setCgId(careGiverInformation.getCaregiverId());
        caregiver.setCode(careGiverInformation.getCaregiverCode());
        caregiver.setUserName(careGiverInformation.getUserName());
        caregiver.setFirstName(careGiverInformation.getFirstName());
        caregiver.setMiddleName(careGiverInformation.getMiddleName());
        caregiver.setLastName(careGiverInformation.getLastName());
        caregiver.setGender(careGiverInformation.getGender());
        caregiver.setPhoneNumber(careGiverInformation.getPhoneNumber());
        return caregiver;
    }
}
