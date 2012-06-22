package org.wv.stepsovc.tools.importer;

import org.motechproject.importer.annotation.CSVImporter;
import org.motechproject.importer.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.commcare.vo.FacilityInformation;
import org.wv.stepsovc.core.services.FacilityService;

import java.util.List;


@CSVImporter(entity = "facility-phonenumber", bean = FacilityInformation.class)
@Component
public class FacilityPhoneNumberImporter {

    @Autowired
    FacilityService facilityService;

    @Post
    public void updateFacilityPhoneNumber(List<FacilityInformation> facilityInformationList) {
        for (FacilityInformation facilityInformation : facilityInformationList) {
            if (!facilityService.updateFacilityPhoneNumber(facilityInformation.getFacilityCode(), facilityInformation.getPhoneNumbers())) {
                System.err.println(facilityInformation.getFacilityCode() + " - is  invalid . This  record is not  imported !! ");
            }
        }

    }
}
