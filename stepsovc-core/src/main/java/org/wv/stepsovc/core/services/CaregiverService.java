package org.wv.stepsovc.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.domain.Caregiver;
import org.wv.stepsovc.core.repository.AllCaregivers;

@Component
public class CaregiverService {

    @Autowired
    AllCaregivers allCaregivers;

    public void addCareGiver(Caregiver caregiver) {
        Caregiver existingCaregiver = allCaregivers.findCaregiverByCode(caregiver.getCode());
        if (existingCaregiver != null) {
            allCaregivers.update(caregiver);
        } else {
            allCaregivers.add(caregiver);
        }
    }

    public boolean updateCaregiverPhoneNumberAndFacilityCode(String caregiverCode, String phoneNumber, String facilityCode) {
        Caregiver caregiver = allCaregivers.findCaregiverByCode(caregiverCode);
        if (caregiver != null) {
            caregiver.setPhoneNumber(phoneNumber);
            caregiver.setFacilityCode(facilityCode);
            allCaregivers.update(caregiver);
            return true;
        } else {
            return false;
        }

    }
}
