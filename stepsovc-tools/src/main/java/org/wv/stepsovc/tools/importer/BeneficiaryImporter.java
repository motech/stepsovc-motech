package org.wv.stepsovc.tools.importer;


import org.apache.log4j.Logger;
import org.motechproject.importer.annotation.CSVImporter;
import org.motechproject.importer.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.BeneficiaryInformation;
import org.wv.stepsovc.tools.dmis.DMISDataProcessor;

import java.util.List;

@CSVImporter(entity = "beneficiary", bean = BeneficiaryInformation.class)
@Component
public class BeneficiaryImporter {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private CommcareGateway commcareGateway;
    @Autowired
    DMISDataProcessor dmisDataProcessor;

    @Post
    public void post(List<BeneficiaryInformation> entities) {
        logger.info("creating  beneficiary  cases in commcare");
        for (BeneficiaryInformation entity : entities) {
            entity.setBeneficiaryName(dmisDataProcessor.decrypt(entity.getBeneficiaryName()));
            entity.setCareGiverName(dmisDataProcessor.decrypt(entity.getCareGiverName()));

            try {
                commcareGateway.createCase(entity);
            } catch (Exception e) {
                logger.error(entity.getBeneficiaryCode() + ", " + entity.getBeneficiaryId() + e);
            }
        }
    }
}