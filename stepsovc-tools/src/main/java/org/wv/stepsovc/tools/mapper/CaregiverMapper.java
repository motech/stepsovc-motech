package org.wv.stepsovc.tools.mapper;

import org.wv.stepsovc.commcare.vo.CareGiverInformation;
import org.wv.stepsovc.core.domain.Caregiver;

public class CaregiverMapper {

    public Caregiver map(CareGiverInformation careGiverInformation) {
        Caregiver caregiver = new Caregiver();
        caregiver.setCode(careGiverInformation.getCode());
        caregiver.setId(careGiverInformation.getId());
        caregiver.setName(careGiverInformation.getName());
        caregiver.setPhoneNumber(careGiverInformation.getPhoneNumber());

        return caregiver;
    }
}
