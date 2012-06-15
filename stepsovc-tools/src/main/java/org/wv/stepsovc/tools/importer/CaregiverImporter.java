package org.wv.stepsovc.tools.importer;

import ch.lambdaj.Lambda;
import org.joda.time.DateTime;
import org.motechproject.importer.annotation.CSVImporter;
import org.motechproject.importer.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.CaregiverInformation;
import org.wv.stepsovc.core.services.CaregiverService;
import org.wv.stepsovc.tools.dmis.DMISDataProcessor;
import org.wv.stepsovc.tools.mapper.CaregiverMapper;

import java.util.List;

import static ch.lambdaj.Lambda.on;

@CSVImporter(entity = "caregiver", bean = CaregiverInformation.class)
@Component
public class CaregiverImporter {
    @Autowired
    private DMISDataProcessor dmisDataProcessor;
    @Autowired
    private CommcareGateway commcareGateway;
    @Autowired
    private CaregiverService caregiverService;

    @Post
    public void post(List<CaregiverInformation> entities) {
        CaregiverMapper caregiverMapper = new CaregiverMapper();
        for (CaregiverInformation entity : entities) {
            entity.setFirstName(dmisDataProcessor.decrypt(entity.getFirstName()));
            entity.setMiddleName(dmisDataProcessor.decrypt(entity.getMiddleName()));
            entity.setLastName(dmisDataProcessor.decrypt(entity.getLastName()));
            entity.setCreationDate(DateTime.now().toString());
            commcareGateway.registerCaregiver(entity);
            caregiverService.addCareGiver(caregiverMapper.map(entity));
        }
        List<String> caregiverIds = Lambda.extract(entities, on(CaregiverInformation.class).getCaregiverId());
        commcareGateway.createOrUpdateGroup(CommcareGateway.ALL_USERS_GROUP, caregiverIds.toArray(new String[]{}));
    }
}
