package org.wv.stepsovc.core.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type == 'Beneficiary'")
public class Beneficiary extends MotechBaseDataObject {
    @JsonProperty
    private String name;
    @JsonProperty
    private String code;
    @JsonProperty
    private String dateOfBirth;
    @JsonProperty
    private String title;
    @JsonProperty
    private String sex;
    @JsonProperty
    private String caregiverCode;

    public String getName() {
        return name;
    }

    public Beneficiary setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Beneficiary setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCaregiverCode() {
        return caregiverCode;
    }

    public void setCaregiverCode(String caregiverCode) {
        this.caregiverCode = caregiverCode;
    }
}
