package org.wv.stepsovc.web.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

import java.util.Date;

@TypeDiscriminator("doc.type == 'Referral'")
public class Referral extends MotechBaseDataObject {
    @JsonProperty
    private String beneficiaryCode;
    @JsonProperty
    private String facilityId;
    @JsonProperty
    private Date visitDate;
    @JsonProperty
    private Date serviceDate;
    @JsonProperty
    private String followupRequired;
    @JsonProperty
    private String artAdherenceCounseling;
    @JsonProperty
    private String condoms;
    @JsonProperty
    private String endOfLifeHospice;
    @JsonProperty
    private String familyPlanning;
    @JsonProperty
    private String hivCounseling;
    @JsonProperty
    private String newDiagnosis;
    @JsonProperty
    private String otherHealthServices;
    @JsonProperty
    private Date followupDate;
    @JsonProperty
    private String painManagement;
    @JsonProperty
    private String pmtct;
    @JsonProperty
    private String sexuallyTransmittedInfection;


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
