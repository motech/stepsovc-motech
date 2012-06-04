package org.wv.stepsovc.tools.importer;

import org.joda.time.DateTime;
import org.motechproject.importer.annotation.CSVImporter;
import org.motechproject.importer.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.CareGiverInformation;
import org.wv.stepsovc.core.services.CaregiverService;
import org.wv.stepsovc.tools.dmis.DMISDataProcessor;
import org.wv.stepsovc.tools.mapper.CaregiverMapper;

import java.util.List;

@CSVImporter(entity = "caregiver", bean = CareGiverInformation.class)
@Component
public class CgImporter {
    @Autowired
    private DMISDataProcessor dmisDataProcessor;
    @Autowired
    private CommcareGateway commcareGateway;
    @Autowired
    private CaregiverService caregiverService;

    @Post
    public void post(List<CareGiverInformation> entities) {
        CaregiverMapper caregiverMapper = new CaregiverMapper();
        for (CareGiverInformation entity : entities) {
            entity.setFirstName(dmisDataProcessor.decrypt(entity.getFirstName()));
            entity.setMiddleName(dmisDataProcessor.decrypt(entity.getMiddleName()));
            entity.setLastName(dmisDataProcessor.decrypt(entity.getLastName()));
            entity.setCreationDate(DateTime.now().toString());
            commcareGateway.registerUser(entity);
            caregiverService.addCareGiver(caregiverMapper.map(entity));
        }
    }
}