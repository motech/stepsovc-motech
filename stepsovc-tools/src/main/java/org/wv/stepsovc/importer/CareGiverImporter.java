package org.wv.stepsovc.importer;

import org.wv.stepsovc.dmis.DMISDataProcessor;
import org.wv.stepsovc.vo.CareGiverInformation;

import java.util.List;

public class CareGiverImporter extends CSV<CareGiverInformation> {

    private DMISDataProcessor dmisDataProcessor = new DMISDataProcessor();

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
        //call Commcaregateway to create  caregiver
    }
}
