package org.wv.stepsovc.commcare.domain;

public class UserData {

    private String firstName;
    private String middleName;
    private String lastName;
    private String caregiverCode;
    private String gender;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCaregiverCode() {
        return caregiverCode;
    }

    public void setCaregiverCode(String caregiverCode) {
        this.caregiverCode = caregiverCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
