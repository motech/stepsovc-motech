package org.wv.stepsovc.core.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type == 'Caregiver'")
public class Caregiver extends MotechBaseDataObject {
    @JsonProperty
    private String cgId;
    @JsonProperty
    private String code;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String middleName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String gender;
    @JsonProperty
    private String phoneNumber;

    public Caregiver() {
    }

    public Caregiver(String cgId, String code, String firstName, String phoneNumber) {
        this.cgId = cgId;
        this.code = code;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }

    public String getCgId() {
        return cgId;
    }

    public void setCgId(String id) {
        this.cgId = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
