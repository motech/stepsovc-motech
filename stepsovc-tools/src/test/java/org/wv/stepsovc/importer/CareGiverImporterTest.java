package org.wv.stepsovc.importer;

import org.junit.Test;
import org.wv.stepsovc.vo.CareGiverInformation;

public class CareGiverImporterTest {

    @Test
    public void shouldImportCareGiversFromCSVFile() {
        CareGiverImporter careGiverImporter = new CareGiverImporter();
        String path = getClass().getResource("/caregiver.csv").getPath();
        careGiverImporter.process(path, CareGiverInformation.class);
    }


}
