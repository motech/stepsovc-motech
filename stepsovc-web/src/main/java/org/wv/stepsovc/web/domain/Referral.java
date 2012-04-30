package org.wv.stepsovc.web.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type == 'Referral'")
public class Referral extends MotechBaseDataObject {
    @JsonProperty
    private boolean active;
    @JsonProperty
    private String beneficiaryCode;
    @JsonProperty
    private String facilityId;
    @JsonProperty
    private String visitDate;
    @JsonProperty
    private String serviceDate;
    @JsonProperty
    private String followupRequired;
    @JsonProperty
    private Service artAdherenceCounseling;
    @JsonProperty
    private Service condoms;
    @JsonProperty
    private Service endOfLifeHospice;
    @JsonProperty
    private Service familyPlanning;
    @JsonProperty
    private Service hivCounseling;
    @JsonProperty
    private Service newDiagnosis;
    @JsonProperty
    private Service otherHealthServices;
    @JsonProperty
    private String followupDate;
    @JsonProperty
    private Service painManagement;
    @JsonProperty
    private Service pmtct;
    @JsonProperty
    private Service sexuallyTransmittedInfection;

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFollowupRequired() {
        return followupRequired;
    }

    public boolean isActive() {
        return active;
    }

    public void setFollowupRequired(String followupRequired) {
        this.followupRequired = followupRequired;
    }

    public String getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(String followupDate) {
        this.followupDate = followupDate;
    }

    public Service getArtAdherenceCounseling() {
        return artAdherenceCounseling;
    }

    public void setArtAdherenceCounseling(Service artAdherenceCounseling) {
        this.artAdherenceCounseling = artAdherenceCounseling;
    }

    public Service getCondoms() {
        return condoms;
    }

    public void setCondoms(Service condoms) {
        this.condoms = condoms;
    }

    public Service getEndOfLifeHospice() {
        return endOfLifeHospice;
    }

    public void setEndOfLifeHospice(Service endOfLifeHospice) {
        this.endOfLifeHospice = endOfLifeHospice;
    }

    public Service getFamilyPlanning() {
        return familyPlanning;
    }

    public void setFamilyPlanning(Service familyPlanning) {
        this.familyPlanning = familyPlanning;
    }

    public Service getHivCounseling() {
        return hivCounseling;
    }

    public void setHivCounseling(Service hivCounseling) {
        this.hivCounseling = hivCounseling;
    }

    public Service getNewDiagnosis() {
        return newDiagnosis;
    }

    public void setNewDiagnosis(Service newDiagnosis) {
        this.newDiagnosis = newDiagnosis;
    }

    public Service getOtherHealthServices() {
        return otherHealthServices;
    }

    public void setOtherHealthServices(Service otherHealthServices) {
        this.otherHealthServices = otherHealthServices;
    }

    public Service getPainManagement() {
        return painManagement;
    }

    public void setPainManagement(Service painManagement) {
        this.painManagement = painManagement;
    }

    public Service getPmtct() {
        return pmtct;
    }

    public void setPmtct(Service pmtct) {
        this.pmtct = pmtct;
    }

    public Service getSexuallyTransmittedInfection() {
        return sexuallyTransmittedInfection;
    }

    public void setSexuallyTransmittedInfection(Service sexuallyTransmittedInfection) {
        this.sexuallyTransmittedInfection = sexuallyTransmittedInfection;
    }

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

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }
}
