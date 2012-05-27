package fixture;

import org.wv.stepsovc.commcare.vo.CareGiverInformation;

public class TestFixture {

//    public BeneficiaryInformation createBeneficiaryInformation() {
//        BeneficiaryInformation beneficiaryInformation = new BeneficiaryInformation();
//
//        beneficiaryInformation.setCode("XYZ");
//        beneficiaryInformation.setName("Albie");
//        beneficiaryInformation.setDob("12-12-1988");
//        beneficiaryInformation.setSex("male");
//        beneficiaryInformation.setTitle("MR");
//        beneficiaryInformation.setReceivingOrganization("XAQ");
//        return beneficiaryInformation;
//
//    }

    public static CareGiverInformation createCareGiverInformation() {
        CareGiverInformation careGiverInformation = new CareGiverInformation();
        careGiverInformation.setCaregiverId("id");
        careGiverInformation.setCaregiverCode("code");
        careGiverInformation.setFirstName("fName");
        careGiverInformation.setMiddleName("mName");
        careGiverInformation.setLastName("lName");
        careGiverInformation.setGender("gender");
        careGiverInformation.setPhoneNumber("phoneNumber");
        return careGiverInformation;
    }

    public static CareGiverInformation createCareGiverInformation(String id, String code) {
        CareGiverInformation careGiverInformation = new CareGiverInformation();
        careGiverInformation.setCaregiverId(id);
        careGiverInformation.setCaregiverCode(code);
        careGiverInformation.setFirstName("fName");
        careGiverInformation.setMiddleName("mName");
        careGiverInformation.setLastName("lName");
        careGiverInformation.setGender("gender");
        careGiverInformation.setPhoneNumber("phoneNumber");
        return careGiverInformation;
    }
}
