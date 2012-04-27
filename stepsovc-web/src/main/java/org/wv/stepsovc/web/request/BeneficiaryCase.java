package org.wv.stepsovc.web.request;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type == 'BeneficiaryCase'")
public class BeneficiaryCase extends MotechBaseDataObject {

    @JsonProperty
    private String beneficiary_name;
    @JsonProperty
    private String beneficiary_code;
    @JsonProperty
    private String beneficiary_dob;
    @JsonProperty
    private String title;
    @JsonProperty
    private String sex;
    @JsonProperty
    private String caregiver_name;
    @JsonProperty
    private String caregiver_id;
    private String receiving_organization;
    private String form_type;

    public String getBeneficiary_dob() {
        return beneficiary_dob;
    }

    public void setBeneficiary_dob(String beneficiary_dob) {
        this.beneficiary_dob = beneficiary_dob;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeneficiary_name() {
        return beneficiary_name;
    }

    public void setBeneficiary_name(String beneficiary_name) {
        this.beneficiary_name = beneficiary_name;
    }

    public String getReceiving_organization() {
        return receiving_organization;
    }

    public void setReceiving_organization(String receiving_organization) {
        this.receiving_organization = receiving_organization;
    }

    public String getCaregiver_name() {
        return caregiver_name;
    }

    public void setCaregiver_name(String caregiver_name) {
        this.caregiver_name = caregiver_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBeneficiary_code() {
        return beneficiary_code;
    }

    public void setBeneficiary_code(String beneficiary_code) {
        this.beneficiary_code = beneficiary_code;
    }

    public String getCaregiver_id() {
        return caregiver_id;
    }

    public void setCaregiver_id(String caregiver_id) {
        this.caregiver_id = caregiver_id;
    }

    public String getForm_type() {
        return form_type;
    }

    public void setForm_type(String form_type) {
        this.form_type = form_type.toString();
    }
}
