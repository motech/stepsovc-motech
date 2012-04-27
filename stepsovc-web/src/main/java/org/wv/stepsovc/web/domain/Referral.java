package org.wv.stepsovc.web.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

import java.util.Date;

@TypeDiscriminator("doc.type == 'Referral'")
public class Referral extends MotechBaseDataObject {
    @JsonProperty("beneficiary_code")
    private String beneficiaryCode;
    @JsonProperty("facility_id")
    private String facilityId;
    @JsonProperty("visit_date")
    private Date visitDate;
    @JsonProperty("referred_date")
    private Date serviceDate;
    @JsonProperty("followup_required")
    private String followupRequired;
    @JsonProperty("art_adherence_counseling")
    private String artAdherenceCounseling;
    @JsonProperty
    private String condoms;
    @JsonProperty("end_of_life_hospice")
    private String endOfLifeHospice;
    @JsonProperty("family_planning")
    private String familyPlanning;
    @JsonProperty("hiv_counseling")
    private String hivCounseling;
    @JsonProperty("new_diagnosis")
    private String newDiagnosis;
    @JsonProperty("other_health_services")
    private String otherHealthServices;
    @JsonProperty("followup_date")
    private Date followupDate;

    public String getFollowupRequired() {
        return followupRequired;
    }

    public void setFollowupRequired(String followupRequired) {
        this.followupRequired = followupRequired;
    }

    public Date getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(Date followupDate) {
        this.followupDate = followupDate;
    }

    public String getArtAdherenceCounseling() {
        return artAdherenceCounseling;
    }

    public void setArtAdherenceCounseling(String artAdherenceCounseling) {
        this.artAdherenceCounseling = artAdherenceCounseling;
    }

    public String getCondoms() {
        return condoms;
    }

    public void setCondoms(String condoms) {
        this.condoms = condoms;
    }

    public String getEndOfLifeHospice() {
        return endOfLifeHospice;
    }

    public void setEndOfLifeHospice(String endOfLifeHospice) {
        this.endOfLifeHospice = endOfLifeHospice;
    }

    public String getFamilyPlanning() {
        return familyPlanning;
    }

    public void setFamilyPlanning(String familyPlanning) {
        this.familyPlanning = familyPlanning;
    }

    public String getHivCounseling() {
        return hivCounseling;
    }

    public void setHivCounseling(String hivCounseling) {
        this.hivCounseling = hivCounseling;
    }

    public String getNewDiagnosis() {
        return newDiagnosis;
    }

    public void setNewDiagnosis(String newDiagnosis) {
        this.newDiagnosis = newDiagnosis;
    }

    public String getOtherHealthServices() {
        return otherHealthServices;
    }

    public void setOtherHealthServices(String otherHealthServices) {
        this.otherHealthServices = otherHealthServices;
    }

    public String getPainManagement() {
        return painManagement;
    }

    public void setPainManagement(String painManagement) {
        this.painManagement = painManagement;
    }

    public String getPmtct() {
        return pmtct;
    }

    public void setPmtct(String pmtct) {
        this.pmtct = pmtct;
    }

    public String getSexuallyTransmittedInfection() {
        return sexuallyTransmittedInfection;
    }

    public void setSexuallyTransmittedInfection(String sexuallyTransmittedInfection) {
        this.sexuallyTransmittedInfection = sexuallyTransmittedInfection;
    }

    @JsonProperty("pain_management")
    private String painManagement;
    @JsonProperty
    private String pmtct;
    @JsonProperty("sexually_transmitted_infection")
    private String sexuallyTransmittedInfection;

    public String getBeneficiaryCode() {
        return beneficiaryCode;
    }

    public void setBeneficiaryCode(String beneficiaryCode) {
        this.beneficiaryCode = beneficiaryCode;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }
}
