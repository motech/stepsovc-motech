package org.wv.stepsovc.tools.importer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.commcare.repository.AllUsers;
import org.wv.stepsovc.core.repository.AllCaregivers;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-Tools.xml")
public class StepsOVCDataImporterIT {

    @Autowired
    private AllUsers allUsers;
    @Autowired
    private AllCaregivers allCaregivers;

    @Test
    public void shouldImportCareGivers() throws InterruptedException {
        createAndAssertCaregiver();
        deleteCareGiverCreated();

    }

    @Test
    public void shouldUpdateCaregiverWithPhoneNumberAndFaciltyCode() {
        createAndAssertCaregiver();
        updateCaregiverWithPhoneNumberAndFacilityCode();
        assertCaregiverPhoneNumber();
        assertCaregiverFacilityCode();
        deleteCareGiverCreated();

    }

    @Test
    public void shouldUpdateExistingCaregiverWithNewFacilityCode() {
        createAndAssertCaregiver();
        updateCaregiverWithPhoneNumberAndFacilityCode();
        String filePath = this.getClass().getResource("/caregiver-phonenumber-facilitycode-FAC002.csv").getPath();
        String[] newArgs = {"caregiver-phonenumber-facilitycode", filePath};
        StepsOVCDataImporter.main(newArgs);
        assertCaregiverFacilityCode();
        deleteCareGiverCreated();

    }

    private void deleteCareGiverCreated() {
        for (int i = 100; i <= 102; i++) {
            allUsers.remove(allUsers.get(String.valueOf(i)));
            allCaregivers.remove(allCaregivers.findCaregiverById(String.valueOf(i)));
        }
    }

    private void assertCareGiversCreated() {
        for (int i = 100; i <= 102; i++) {
            assertTrue(allUsers.contains(String.valueOf(i)));
            assertNotNull(allCaregivers.findCaregiverById(String.valueOf(i)));
        }
    }

    private void assertCaregiverPhoneNumber() {
        for (int i = 100; i <= 102; i++) {
            assertNotNull(allCaregivers.findCaregiverById(String.valueOf(i)).getPhoneNumber());
        }
    }

    private void assertCaregiverFacilityCode() {
        for (int i = 100; i <= 102; i++) {
            assertNotNull(allCaregivers.findCaregiverById(String.valueOf(i)).getFacilityCode());
        }
    }

    private void assertCareGiversNotPresent() {
        for (int i = 100; i <= 102; i++) {
            assertFalse(allUsers.contains(String.valueOf(i)));
            assertNull(allCaregivers.findCaregiverById(String.valueOf(i)));
        }
    }

    private void createAndAssertCaregiver() {
        assertCareGiversNotPresent();
        String filePath = this.getClass().getResource("/caregiver.csv").getPath();
        String[] args = {"caregiver", filePath};
        StepsOVCDataImporter.main(args);
        assertCareGiversCreated();
    }

    private void updateCaregiverWithPhoneNumberAndFacilityCode() {
        String filePath = this.getClass().getResource("/caregiver-phonenumber-facilitycode-FAC001.csv").getPath();
        String[] newArgs = {"caregiver-phonenumber-facilitycode", filePath};
        StepsOVCDataImporter.main(newArgs);
    }


}
