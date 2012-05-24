package org.wv.stepsovc.tools.importer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.commcare.repository.AllUsers;
import org.wv.stepsovc.core.repository.AllCaregivers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-Tools.xml")
public class StepsOVCDataImporterIT {

    @Autowired
    private AllUsers allUsers;
    @Autowired
    private AllCaregivers allCaregivers;

    // Ids are from csv file used for testing
    private String caregiver1UUID = "111111";
    private String caregiver2UUID = "222222";

    @Test
    public void shouldImportCareGivers() throws InterruptedException {
        String filePath = "/Users/gee/Projects/stepsovc-motech/stepsovc-tools/src/main/resources/caregiver.csv";
        String[] args = {"caregiver", filePath};

        assertCareGiversNotPresent();

        StepsOVCDataImporter.main(args);

        Thread.sleep(2000);

        assertCareGiversCreated();
        deleteCareGiverCreated();

    }

    private void deleteCareGiverCreated() {
        allUsers.remove(allUsers.get(caregiver1UUID));
        allUsers.remove(allUsers.get(caregiver2UUID));
        allCaregivers.remove(allCaregivers.get(caregiver1UUID));
        allCaregivers.remove(allCaregivers.get(caregiver2UUID));
    }

    private void assertCareGiversCreated() {
        assertTrue(allUsers.contains(caregiver1UUID));
        assertTrue(allUsers.contains(caregiver2UUID));
        assertTrue(allCaregivers.contains(caregiver1UUID));
        assertTrue(allCaregivers.contains(caregiver2UUID));
    }

    private void assertCareGiversNotPresent() {
        assertFalse(allUsers.contains(caregiver1UUID));
        assertFalse(allUsers.contains(caregiver2UUID));
        assertFalse(allCaregivers.contains(caregiver1UUID));
        assertFalse(allCaregivers.contains(caregiver2UUID));
    }


}
