package org.wv.stepsovc.tools.importer;

import org.motechproject.importer.annotation.CSVImporter;
import org.motechproject.importer.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.commcare.vo.CaregiverInformation;
import org.wv.stepsovc.core.services.CaregiverService;

import java.util.List;

@CSVImporter(entity = "caregiver-phonenumber-facilitycode", bean = CaregiverInformation.class)
@Component
public class CaregiverPhoneNumberAndFacilityCodeImporter {
    @Autowired
    private CaregiverService caregiverService;

    @Post
    public void importCaregiverPhoneNumbers(List<CaregiverInformation> caregiverList) {
        for (CaregiverInformation careGiverInformation : caregiverList) {
            caregiverService.updateCaregiverPhoneNumberAndFacilityCode(careGiverInformation.getCaregiverCode(), careGiverInformation.getPhoneNumber(), careGiverInformation.getFacilityCode());
        }
    }

}
