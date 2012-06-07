package fixture;

import org.wv.stepsovc.commcare.domain.CaseType;
import org.wv.stepsovc.commcare.vo.BeneficiaryInformation;
import org.wv.stepsovc.commcare.vo.CaregiverInformation;
import org.wv.stepsovc.commcare.vo.FacilityInformation;

public class TestFixture {

    public static CaregiverInformation createCareGiverInformation() {
        CaregiverInformation careGiverInformation = new CaregiverInformation();
        careGiverInformation.setCaregiverId("id");
        careGiverInformation.setCaregiverCode("code");
        careGiverInformation.setUserName("uName");
        careGiverInformation.setFirstName("fName");
        careGiverInformation.setMiddleName("mName");
        careGiverInformation.setLastName("lName");
        careGiverInformation.setGender("gender");
        careGiverInformation.setPhoneNumber("11111");
        careGiverInformation.setCreationDate("01-01-2012");
        return careGiverInformation;
    }

    public static CaregiverInformation createCareGiverInformation(String id, String code) {
        CaregiverInformation careGiverInformation = new CaregiverInformation();
        careGiverInformation.setCaregiverId(id);
        careGiverInformation.setCaregiverCode(code);
        careGiverInformation.setFirstName("fName");
        careGiverInformation.setMiddleName("mName");
        careGiverInformation.setLastName("lName");
        careGiverInformation.setGender("gender");
        careGiverInformation.setPhoneNumber("11111");
        careGiverInformation.setCreationDate("01-01-2012");
        return careGiverInformation;
    }


    public static FacilityInformation createFacilityInformation() {
        FacilityInformation facilityInformation = new FacilityInformation();
        facilityInformation.setFacilityId("FAC1");
        facilityInformation.setFacilityCode("someFacilityCode");
        facilityInformation.setConstituency("someFacilityConstituency");
        facilityInformation.setDistrict("someFacilityDistrict");
        facilityInformation.setVillage("someFacilityVillage");
        facilityInformation.setWard("someFacilityWard");
        facilityInformation.setMandatoryPhoneNumber("1234567890");
        facilityInformation.setOptionalPhoneNumber1("987654321");
        facilityInformation.setOptionalPhoneNumber2("23142345645");
        facilityInformation.setOptionalPhoneNumber3("253767569");
        return facilityInformation;
    }

    public static BeneficiaryInformation getBeneficiaryInformation(String caregiverId, String caregiverCode, String caseId, String caseName, String beneficiaryCode, String caregiverName, String ownerId) {

        BeneficiaryInformation beneficiaryInformation = new BeneficiaryInformation();
        beneficiaryInformation.setBeneficiaryId(caseId);
        beneficiaryInformation.setBeneficiaryCode(beneficiaryCode);
        beneficiaryInformation.setBeneficiaryName(caseName);
        beneficiaryInformation.setBeneficiaryDob("12-12-1988");
        beneficiaryInformation.setBeneficiarySex("male");
        beneficiaryInformation.setBeneficiaryTitle("MR");
        beneficiaryInformation.setReceivingOrganization("XAQ");
        beneficiaryInformation.setCareGiverCode(caregiverCode);
        beneficiaryInformation.setCareGiverId(caregiverId);
        beneficiaryInformation.setCareGiverName(caregiverName);
        beneficiaryInformation.setCaseType(CaseType.BENEFICIARY_CASE.getType());
        beneficiaryInformation.setDateModified("2012-05-02T22:18:45.071+05:30");
        beneficiaryInformation.setOwnerId(ownerId);
        return beneficiaryInformation;
    }
}

