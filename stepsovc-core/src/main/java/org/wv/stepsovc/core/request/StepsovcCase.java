package org.wv.stepsovc.core.request;

public class StepsovcCase {

    private String case_type;
    private String case_id;

    /* Facility Case starts */
    private String facility_code;
    private String facility_name;
    private String service_unavailable_from;
    private String service_unavailable_to;
    private String service_unavailable_reason;
    /* Facility Case ends */

    /* Beneficiary Case starts */
    private String beneficiary_name;
    private String beneficiary_dob;

    private String title;
    private String sex;
    private String caregiver_name;
    private String caregiver_code;
    private String receiving_organization;
    private String form_type;
    private String service_date;

    private String service_details;

    private String followup_required;
    private String followup_date;
    private String visit_date;
    private String art_adherence_counseling;
    private String condoms;
    private String end_of_life_hospice;
    private String family_planning;
    private String hiv_counseling;
    private String new_diagnosis;
    private String other_health_services;
    private String pain_management;
    private String pmtct;
    private String sexually_transmitted_infection;
    private String other_health_service_na_reason;
    private String art_adherence_na_reason;
    private String pain_management_na_reason;
    private String sexually_transmitted_na_reason;
    private String family_planning_na_reason;
    private String new_diagnosis_na_reason;
    private String condoms_na_reason;
    private String end_of_life_hospice_na_reason;
    private String hiv_counseling_na_reason;
    private String pmtct_na_reason;
    private String owner_id;
    private String date_modified;
    private String user_id;
    /* Beneficiary Case ends */
    public String getService_details() {
        return service_details;
    }

    public void setService_details(String service_details) {
        this.service_details = service_details;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getCase_type() {
        return case_type;
    }

    public void setCase_type(String case_type) {
        this.case_type = case_type;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(String date_modified) {
        this.date_modified = date_modified;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private String beneficiary_code;

    public String getFacility_code() {
        return facility_code;
    }

    public void setFacility_code(String facility_code) {
        this.facility_code = facility_code;
    }

    public String getService_unavailable_from() {
        return service_unavailable_from;
    }

    public void setService_unavailable_from(String service_unavailable_from) {
        this.service_unavailable_from = service_unavailable_from;
    }

    public String getService_unavailable_to() {
        return service_unavailable_to;
    }

    public void setService_unavailable_to(String service_unavailable_to) {
        this.service_unavailable_to = service_unavailable_to;
    }

    public String getService_unavailable_reason() {
        return service_unavailable_reason;
    }

    public void setService_unavailable_reason(String service_unavailable_reason) {
        this.service_unavailable_reason = service_unavailable_reason;
    }

    public String getOther_health_service_na_reason() {
        return other_health_service_na_reason;
    }

    public void setOther_health_service_na_reason(String other_health_service_na_reason) {
        this.other_health_service_na_reason = other_health_service_na_reason;
    }

    public String getArt_adherence_na_reason() {
        return art_adherence_na_reason;
    }

    public void setArt_adherence_na_reason(String art_adherence_na_reason) {
        this.art_adherence_na_reason = art_adherence_na_reason;
    }

    public String getPain_management_na_reason() {
        return pain_management_na_reason;
    }

    public void setPain_management_na_reason(String pain_management_na_reason) {
        this.pain_management_na_reason = pain_management_na_reason;
    }

    public String getSexually_transmitted_na_reason() {
        return sexually_transmitted_na_reason;
    }

    public void setSexually_transmitted_na_reason(String sexually_transmitted_na_reason) {
        this.sexually_transmitted_na_reason = sexually_transmitted_na_reason;
    }

    public String getFamily_planning_na_reason() {
        return family_planning_na_reason;
    }

    public void setFamily_planning_na_reason(String family_planning_na_reason) {
        this.family_planning_na_reason = family_planning_na_reason;
    }

    public String getNew_diagnosis_na_reason() {
        return new_diagnosis_na_reason;
    }

    public void setNew_diagnosis_na_reason(String new_diagnosis_na_reason) {
        this.new_diagnosis_na_reason = new_diagnosis_na_reason;
    }

    public String getCondoms_na_reason() {
        return condoms_na_reason;
    }

    public void setCondoms_na_reason(String condoms_na_reason) {
        this.condoms_na_reason = condoms_na_reason;
    }

    public String getEnd_of_life_hospice_na_reason() {
        return end_of_life_hospice_na_reason;
    }

    public void setEnd_of_life_hospice_na_reason(String end_of_life_hospice_na_reason) {
        this.end_of_life_hospice_na_reason = end_of_life_hospice_na_reason;
    }

    public String getHiv_counseling_na_reason() {
        return hiv_counseling_na_reason;
    }

    public void setHiv_counseling_na_reason(String hiv_counseling_na_reason) {
        this.hiv_counseling_na_reason = hiv_counseling_na_reason;
    }

    public String getPmtct_na_reason() {
        return pmtct_na_reason;
    }

    public void setPmtct_na_reason(String pmtct_na_reason) {
        this.pmtct_na_reason = pmtct_na_reason;
    }

    public String getService_date() {
        return service_date;
    }

    public void setService_date(String service_date) {
        this.service_date = service_date;
    }

    public String getFollowup_required() {
        return followup_required;
    }

    public void setFollowup_required(String followup_required) {
        this.followup_required = followup_required;
    }

    public String getFollowup_date() {
        return followup_date;
    }

    public void setFollowup_date(String followup_date) {
        this.followup_date = followup_date;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getArt_adherence_counseling() {
        return art_adherence_counseling;
    }

    public void setArt_adherence_counseling(String art_adherence_counseling) {
        this.art_adherence_counseling = art_adherence_counseling;
    }

    public String getCondoms() {
        return condoms;
    }

    public void setCondoms(String condoms) {
        this.condoms = condoms;
    }

    public String getEnd_of_life_hospice() {
        return end_of_life_hospice;
    }

    public void setEnd_of_life_hospice(String end_of_life_hospice) {
        this.end_of_life_hospice = end_of_life_hospice;
    }

    public String getFamily_planning() {
        return family_planning;
    }

    public void setFamily_planning(String family_planning) {
        this.family_planning = family_planning;
    }

    public String getHiv_counseling() {
        return hiv_counseling;
    }

    public void setHiv_counseling(String hiv_counseling) {
        this.hiv_counseling = hiv_counseling;
    }

    public String getNew_diagnosis() {
        return new_diagnosis;
    }

    public void setNew_diagnosis(String new_diagnosis) {
        this.new_diagnosis = new_diagnosis;
    }

    public String getOther_health_services() {
        return other_health_services;
    }

    public void setOther_health_services(String other_health_services) {
        this.other_health_services = other_health_services;
    }

    public String getPain_management() {
        return pain_management;
    }

    public void setPain_management(String pain_management) {
        this.pain_management = pain_management;
    }

    public String getPmtct() {
        return pmtct;
    }

    public void setPmtct(String pmtct) {
        this.pmtct = pmtct;
    }

    public String getSexually_transmitted_infection() {
        return sexually_transmitted_infection;
    }

    public void setSexually_transmitted_infection(String sexually_transmitted_infection) {
        this.sexually_transmitted_infection = sexually_transmitted_infection;
    }

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

    public String getCaregiver_code() {
        return caregiver_code;
    }

    public void setCaregiver_code(String caregiver_code) {
        this.caregiver_code = caregiver_code;
    }

    public String getForm_type() {
        return form_type;
    }

    public void setForm_type(String form_type) {
        this.form_type = form_type.toString();
    }
}
