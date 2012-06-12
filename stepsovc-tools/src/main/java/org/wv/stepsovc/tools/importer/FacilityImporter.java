package org.wv.stepsovc.tools.importer;

import org.joda.time.DateTime;
import org.motechproject.importer.annotation.CSVImporter;
import org.motechproject.importer.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.FacilityInformation;
import org.wv.stepsovc.core.mapper.FacilityMapper;
import org.wv.stepsovc.core.services.FacilityService;

import java.util.List;
import java.util.UUID;

@CSVImporter(entity = "facility", bean = FacilityInformation.class)
@Component
public class FacilityImporter {

    @Autowired
    private CommcareGateway commcareGateway;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private FacilityMapper facilityMapper;

    @Post
    public void importFacilities(List<FacilityInformation> facilityList) {
        for (FacilityInformation facilityInformation : facilityList) {
            facilityInformation.setCreationDate(DateTime.now().toString());
            facilityInformation.setId(UUID.randomUUID().toString());
            commcareGateway.registerFacility(facilityInformation);
            facilityService.addFacility(facilityMapper.mapFacilityInformationToFacility(facilityInformation));
        }

    }
}
