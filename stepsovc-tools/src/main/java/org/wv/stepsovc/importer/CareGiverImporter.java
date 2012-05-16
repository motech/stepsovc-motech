package org.wv.stepsovc.importer;

import org.motechproject.importer.CSVImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.dmis.DMISDataProcessor;
import org.wv.stepsovc.vo.CareGiverInformation;

import java.util.List;

public class CareGiverImporter extends CSVImporter<CareGiverInformation> {

    private DMISDataProcessor dmisDataProcessor;
    @Autowired
    private CommcareGateway commcareGateway;

    public CareGiverImporter() {
        this.dmisDataProcessor = new DMISDataProcessor();
    }

    public static void main(String args[]) {
        String filePath = args[0];
        CareGiverImporter careGiverImporter = new CareGiverImporter();
        careGiverImporter.process(filePath, CareGiverInformation.class);
    }

    @Override
    public List<CareGiverInformation> transform(List<CareGiverInformation> careGivers) {
        for (CareGiverInformation careGiver : careGivers) {
            careGiver.setName(dmisDataProcessor.decrypt(careGiver.getName()));
        }
        return careGivers;
    }

    public void post(List<CareGiverInformation> entities) {
        for (CareGiverInformation entity : entities) {
            commcareGateway.registerUser(entity);
        }
    }
}
