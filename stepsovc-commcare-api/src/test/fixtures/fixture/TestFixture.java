package fixture;

import org.wv.stepsovc.commcare.vo.CareGiverInformation;

public class TestFixture {

    public static CareGiverInformation createCareGiverInformation() {
        CareGiverInformation careGiverInformation = new CareGiverInformation();
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

    public static CareGiverInformation createCareGiverInformation(String id, String code) {
        CareGiverInformation careGiverInformation = new CareGiverInformation();
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
}
