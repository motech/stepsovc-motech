package org.wv.stepsovc.tools.importer;

import org.junit.Ignore;
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

    @Ignore(value = "Geetha : failing test - fix it and unignore")
    @Test
    public void shouldImportCareGivers() throws InterruptedException {
        String filePath = "/Users/gee/Projects/stepsovc-motech/stepsovc-tools/src/main/resources/caregiver.csv";
        String[] args = {"caregiver", filePath};

        String caregiver1UUID = "111111";
        String caregiver2UUID = "222222";

        assertFalse(allUsers.contains(caregiver1UUID));
        assertFalse(allUsers.contains(caregiver2UUID));

        StepsOVCDataImporter.main(args);

        Thread.sleep(60000);

//        User newCareGiver1 = allUsers.get(caregiver1UUID);
//        User newCareGiver2 = allUsers.get(caregiver2UUID);
        assertTrue(allUsers.contains(caregiver1UUID));
        assertTrue(allUsers.contains(caregiver2UUID));
        assertTrue(allCaregivers.contains(caregiver2UUID));
//        allUsers.remove(newCareGiver1);
//        allUsers.remove(newCareGiver2);

    }


}
