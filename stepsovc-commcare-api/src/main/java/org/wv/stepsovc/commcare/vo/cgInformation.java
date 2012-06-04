package org.wv.stepsovc.commcare.vo;


public class cgInformation {

    /* primary key column cg_id - from DMIS (id)*/
    private String caregiverId;

    /* care giver full code - from DMIS*/
    private String caregiverCode;

    /* first name of the care giver - from DMIS */
    private String firstName;

    /* middle name of the care giver - from DMIS */
    private String middleName;

    /* last name of the care giver - from DMIS */
    private String lastName;

    /* gender of the care giver - from DMIS */
    private String gender;

    /* phone number of care giver - from DMIS */
    private String phoneNumber;

    /* user name to login - last five digits of caregiver code from DMIS*/
    private String userName;

    /* password to login - generated */
    private String password;

    private String creationDate;

    private String facilityCode;

    public String getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }

    public String getCaregiverCode() {
        return caregiverCode;
    }

    public void setCaregiverCode(String caregiverCode) {
        this.caregiverCode = caregiverCode;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }
}
