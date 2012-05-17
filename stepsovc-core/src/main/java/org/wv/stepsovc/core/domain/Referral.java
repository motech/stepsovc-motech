package org.wv.stepsovc.core.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.wv.stepsovc.core.domain.ServiceType.*;

@TypeDiscriminator("doc.type == 'Referral'")
public class Referral extends MotechBaseDataObject {

    @JsonProperty
    private String ovcId;

    @JsonProperty
    private String beneficiaryCode;
    @JsonProperty
    private String facilityCode;
    @JsonProperty
    private String visitDate;
    @JsonProperty
    private String serviceDate;
    @JsonProperty
    private String followupRequired;
    @JsonProperty
    private String followupDate;
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
    private Service painManagement;
    @JsonProperty
    private Service sexuallyTransmittedInfection;
    @JsonProperty
    private Service pmtct;
    @JsonProperty
    private String serviceDetails;


    public static final String META_FACILITY_ID = "facilityId";

    public static final String VISIT_NAME = "Referral";

    @JsonProperty
    private boolean active;
    public String getOvcId() {
        return ovcId;
    }
    public Referral setOvcId(String ovcId) {
        this.ovcId = ovcId;
        return this;
    }

    public Referral setActive(boolean active) {
        this.active = active;
        return this;
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

    public Referral setFamilyPlanning(Service familyPlanning) {
        this.familyPlanning = familyPlanning;
        return this;
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

    public String getFacilityCode() {
        return facilityCode;
    }

    public Referral setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
        return this;
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

    public Referral setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
        return this;
    }

    public Map<String,Object> appointmentDataMap() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(META_FACILITY_ID, facilityCode);
        return params;
    }

    public String getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public List<String> servicesReferred() {
        List<String> serviceCodes = new ArrayList<String>();
        if(newDiagnosis.isReferred())
            serviceCodes.add(NEW_DIAGNOSIS.getCode());
        if(endOfLifeHospice.isReferred())
            serviceCodes.add(END_OF_LIFE_HOSPICE.getCode());
        if(hivCounseling.isReferred())
            serviceCodes.add(HIVE_COUNSELING.getCode());
        if(familyPlanning.isReferred())
            serviceCodes.add(FAMILY_PLANNING.getCode());
        if(sexuallyTransmittedInfection.isReferred())
            serviceCodes.add(SEXUALLY_TRANSMITTED_INFEC.getCode());
        if(artAdherenceCounseling.isReferred())
            serviceCodes.add(ART_ADHERENCE.getCode());
        if(painManagement.isReferred())
            serviceCodes.add(PAIN_MANAGEMENT.getCode());
        if(pmtct.isReferred())
            serviceCodes.add(PMTCT.getCode());
        if(condoms.isReferred())
            serviceCodes.add(CONDOMS.getCode());
        if(otherHealthServices.isReferred())
            serviceCodes.add(OTHER_HEALTH_SERVICES.getCode());

        return serviceCodes;
    }
}
