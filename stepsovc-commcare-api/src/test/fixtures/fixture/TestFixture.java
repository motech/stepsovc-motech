package fixture;

import org.wv.stepsovc.commcare.vo.cgInformation;

public class TestFixture {

    public static cgInformation createCareGiverInformation() {
        cgInformation careGiverInformation = new cgInformation();
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

    public static cgInformation createCareGiverInformation(String id, String code) {
        cgInformation careGiverInformation = new cgInformation();
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
