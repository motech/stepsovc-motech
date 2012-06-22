package org.wv.stepsovc.tools.importer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.repository.AllGroups;
import org.wv.stepsovc.commcare.repository.AllUsers;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.repository.AllCaregivers;
import org.wv.stepsovc.core.repository.AllFacilities;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-Tools.xml")
@Ignore
public class StepsOVCDataImporterIT {

    @Autowired
    private AllUsers allUsers;
    @Autowired
    private AllCaregivers allCaregivers;
    @Autowired
    private AllFacilities allFacilities;

    @Autowired
    private AllBeneficiaries allBeneficiaries;

    private String beneficiaryId1 = "00146713-7772-43e0-9d47-3a7200953cea";

    private String beneficiaryId2 = "001723a8-1b83-47f3-a7a1-0d2907f00d49";

    String commcareHqUrl = "http://localhost:5984/commcarehq/";
    @Autowired
    private AllGroups allGroups;

    @Test
    public void shouldImportCareGivers() throws InterruptedException {
        createAndAssertCaregiver();
        deleteCareGiverCreated();
        assertAllCaregiversAreAddedToAllUserGroup();

    }

    private void assertAllCaregiversAreAddedToAllUserGroup() {
        List<String> userList = Arrays.asList(allGroups.getGroupByName(CommcareGateway.ALL_USERS_GROUP).getUsers());
        for (int i = 100; i <= 102; i++) {
            userList.contains(String.valueOf(i));
        }
    }

    @Test
    public void shouldUpdateCaregiverWithPhoneNumberAndFaciltyCode() {
        createAndAssertCaregiver();
        updateCaregiverWithPhoneNumberAndFacilityCode("/caregiver-phonenumber-facilitycode-FAC001.csv");
        assertCaregiverPhoneNumber();
        assertCaregiverFacilityCode();
        deleteCareGiverCreated();

    }

    @Test
    public void shouldNotUpdatePhoneNumberForInvalidCaregiver() {
        createAndAssertCaregiver();
        updateCaregiverWithPhoneNumberAndFacilityCode("/caregiver-phonenumber-update-with-invalid-code.csv");
        assertNotNull(allCaregivers.findCaregiverById(String.valueOf(100)).getPhoneNumber());
        assertNull(allCaregivers.findCaregiverById(String.valueOf(101)).getPhoneNumber());
        assertNotNull(allCaregivers.findCaregiverById(String.valueOf(102)).getPhoneNumber());
        assertNotNull(allCaregivers.findCaregiverById(String.valueOf(100)).getFacilityCode());
        assertNull(allCaregivers.findCaregiverById(String.valueOf(101)).getFacilityCode());
        assertNotNull(allCaregivers.findCaregiverById(String.valueOf(102)).getFacilityCode());
        deleteCareGiverCreated();
    }

    @Test
    public void shouldUpdateExistingCaregiverWithNewFacilityCode() {
        createAndAssertCaregiver();
        updateCaregiverWithPhoneNumberAndFacilityCode("/caregiver-phonenumber-facilitycode-FAC001.csv");
        String filePath = this.getClass().getResource("/caregiver-phonenumber-facilitycode-FAC002.csv").getPath();
        String[] newArgs = {"caregiver-phonenumber-facilitycode", filePath};
        StepsOVCDataImporter.main(newArgs);
        assertCaregiverFacilityCode();
        deleteCareGiverCreated();

    }

    @Test
    //When you  run this  test  remember to  delete the  facility  case  manually  created in commcarehq-couchdb,The test  does not
    // takecare of  deleting the facility case
    public void shouldImportFacilities() {
        createFacility();
        deleteFacilityCreated();
        assertNewFacilitiesHaveNewGroupsCreatedAndAllFacilityGroupsAreAddedToAllUsersGroup();
    }

    private void createFacility() {
        assertFacilitiesNotPresent();
        String filePath = this.getClass().getResource("/facility.csv").getPath();
        String[] newArgs = {"facility", filePath};
        StepsOVCDataImporter.main(newArgs);
        assertFacilitiesPresent();
    }

    @Test
    public void shouldUpdateFacilityPhoneNumbers() {
        createFacility();
        String filePath = this.getClass().getResource("/facility-phonenumber.csv").getPath();
        String[] newArgs = {"facility-phonenumber", filePath};
        StepsOVCDataImporter.main(newArgs);
        assertFacilitiesPhoneNumbers();
        deleteFacilityCreated();
    }

    @Test
    public void shouldNotUpdatePhoneNumberForInvalidFacility() {
        createFacility();
        String filePath = this.getClass().getResource("/facility-phonenumber-with-invalid-facility-code.csv").getPath();
        String[] newArgs = {"facility-phonenumber", filePath};
        StepsOVCDataImporter.main(newArgs);
        assertTrue(allFacilities.findFacilityByCode("1").getPhoneNumbers().contains("99"));
        assertTrue(allFacilities.findFacilityByCode("2").getPhoneNumbers().contains("2"));
        assertTrue(allFacilities.findFacilityByCode("3").getPhoneNumbers().contains("77"));
        deleteFacilityCreated();
    }

    private void assertFacilitiesPhoneNumbers() {
        List<String> phoneNumbers = allFacilities.findFacilityByCode("1").getPhoneNumbers();
        assertTrue(phoneNumbers.contains("99"));
    }

    private void assertNewFacilitiesHaveNewGroupsCreatedAndAllFacilityGroupsAreAddedToAllUsersGroup() {
        List<String> userList = Arrays.asList(allGroups.getGroupByName(CommcareGateway.ALL_USERS_GROUP).getUsers());
        for (int i = 1; i <= 3; i++) {
            assertNotNull(allGroups.getGroupByName(String.valueOf(i)));
            userList.contains(String.valueOf(i));
        }
    }

    @Test
    public void shouldImportBeneficiaries() throws InterruptedException {
        createAndAssertBeneficiary();
        deleteBeneficiariesCreated();
    }


    private void deleteFacilityCreated() {
        for (int i = 1; i <= 3; i++) {
            allUsers.remove(allUsers.getUserByName(String.valueOf(i) + "@stepsovc.commcarehq.org"));
            allFacilities.remove(allFacilities.findFacilityByCode(String.valueOf(i)));
        }
    }

    private void assertFacilitiesNotPresent() {
        for (int i = 1; i <= 3; i++) {
            assertNull(allUsers.getUserByName(String.valueOf(i)));
            assertNull(allFacilities.findFacilityByCode(String.valueOf(i)));
        }
    }

    private void assertFacilitiesPresent() {
        for (int i = 1; i <= 3; i++) {
            assertNotNull(allUsers.getUserByName(String.valueOf(i) + "@stepsovc.commcarehq.org"));
            assertNotNull(allFacilities.findFacilityByCode(String.valueOf(i)));
        }
    }


    private void deleteCareGiverCreated() {
        for (int i = 100; i <= 102; i++) {
            allUsers.remove(allUsers.get(String.valueOf(i)));
            allCaregivers.remove(allCaregivers.findCaregiverById(String.valueOf(i)));
        }
    }

    private void assertCareGiversCreated() {
        safeSleep();
        for (int i = 100; i <= 102; i++) {
            assertTrue(allUsers.contains(String.valueOf(i)));
            assertNotNull(allCaregivers.findCaregiverById(String.valueOf(i)));
        }
    }

    private void assertCaregiverPhoneNumber() {
        safeSleep();
        for (int i = 100; i <= 102; i++) {
            assertNotNull(allCaregivers.findCaregiverById(String.valueOf(i)).getPhoneNumber());
        }
    }

    private void assertCaregiverFacilityCode() {
        safeSleep();
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

    private void safeSleep() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void createAndAssertCaregiver() {
        assertCareGiversNotPresent();
        String filePath = this.getClass().getResource("/caregiver.csv").getPath();
        String[] args = {"caregiver", filePath};
        StepsOVCDataImporter.main(args);
        assertCareGiversCreated();
    }

    private void updateCaregiverWithPhoneNumberAndFacilityCode(String csvFileName) {
        String filePath = this.getClass().getResource(csvFileName).getPath();
        String[] newArgs = {"caregiver-phonenumber-facilitycode", filePath};
        StepsOVCDataImporter.main(newArgs);
    }

    public void deleteBeneficiariesCreated() {
        for (int i = 1; i <= 2; i++) {
            if (allBeneficiaries.findBeneficiaryByCode(String.valueOf(i)) != null)
                allBeneficiaries.remove(allBeneficiaries.findBeneficiaryByCode(String.valueOf(i)));
        }
        deleteCasesCreatedInCommcareHq(beneficiaryId1);
        deleteCasesCreatedInCommcareHq(beneficiaryId2);
    }


    private void createAndAssertBeneficiary() throws InterruptedException {
        assertBeneficiariesNotPresent();
        assertCareGiversNotPresent();
        String filePath = this.getClass().getResource("/beneficiary.csv").getPath();
        String[] args = {"beneficiary", filePath};
        StepsOVCDataImporter.main(args);
        assertBeneficiariesCreated();
    }

    private void assertBeneficiariesCreated() throws InterruptedException {
        safeSleep();
        for (int i = 1; i <= 2; i++) {
            assertNotNull(allBeneficiaries.findBeneficiaryByCode(String.valueOf(i)));
        }
    }

    private int deleteCasesCreatedInCommcareHq(String documentId) {
        GetMethod getMethod = new GetMethod(commcareHqUrl + documentId + "?revs_info=true");
        int status = 0;
        String rev_Id = "0";
        HttpClient httpClient = new HttpClient();
        try {
            int getStatus = httpClient.executeMethod(getMethod);
            assertEquals(200, getStatus);
            String responseBodyAsString = getMethod.getResponseBodyAsString();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responseBodyAsString);
            rev_Id = jsonNode.get("_rev").getValueAsText();
            DeleteMethod deleteMethod = new DeleteMethod(commcareHqUrl + documentId + "?rev=" + rev_Id);
            status = httpClient.executeMethod(deleteMethod);
            assertEquals(200, status);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return status;
    }


    private void assertBeneficiariesNotPresent() {
        for (int i = 1; i <= 2; i++) {
            assertNull(allBeneficiaries.findBeneficiaryByCode(String.valueOf(i)));
        }
    }


}
