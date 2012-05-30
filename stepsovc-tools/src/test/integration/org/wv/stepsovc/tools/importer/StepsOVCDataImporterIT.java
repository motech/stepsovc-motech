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
@ContextConfiguration("classpath:applicationContext-Tools.xml")
public class StepsOVCDataImporterIT {

    @Autowired
    private AllUsers allUsers;
    @Autowired
    private AllCaregivers allCaregivers;

    @Test
    public void shouldImportCareGivers() throws InterruptedException {
        String filePath = this.getClass().getResource("/caregiver.csv").getPath();
        String[] args = {"caregiver", filePath};

        assertCareGiversNotPresent();

        StepsOVCDataImporter.main(args);


        assertCareGiversCreated();
        deleteCareGiverCreated();

    }

    private void deleteCareGiverCreated() {
        for (int i = 100; i <= 102; i++) {
            allUsers.remove(allUsers.get(String.valueOf(i)));
            allCaregivers.remove(allCaregivers.get(String.valueOf(i)));
        }
    }

    private void assertCareGiversCreated() {
        for (int i = 100; i <= 102; i++) {
            assertTrue(allUsers.contains(String.valueOf(i)));
            assertTrue(allCaregivers.contains(String.valueOf(i)));
        }
    }

    private void assertCareGiversNotPresent() {
        for (int i = 100; i <= 102; i++) {
            assertFalse(allUsers.contains(String.valueOf(i)));
            assertFalse(allCaregivers.contains(String.valueOf(i)));
        }
    }


}
