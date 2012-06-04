package fixture;

import org.wv.stepsovc.commcare.vo.CaregiverInformation;

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
}
