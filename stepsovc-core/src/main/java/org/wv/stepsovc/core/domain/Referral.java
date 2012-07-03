package org.wv.stepsovc.core.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.LocalDate;
import org.motechproject.model.MotechBaseDataObject;
import org.motechproject.util.DateUtil;
import org.wv.stepsovc.core.utils.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.lambdaj.Lambda.*;

@TypeDiscriminator("doc.type == 'Referral'")
public class Referral extends MotechBaseDataObject {
    @JsonProperty
    private String ovcId;
    @JsonProperty
    private String beneficiaryCode;
    @JsonProperty
    private String facilityCode;
    @JsonProperty
    private String cgId;
    @JsonProperty
    private String visitDate;
    @JsonProperty
    private String serviceDate;
    @JsonProperty
    private String signedDate;
    @JsonProperty
    private String followupRequired;
    @JsonProperty
    private String followupDate;
    @JsonProperty
    private Map<String, Service> referredServices;
    @JsonProperty
    private String serviceDetails;
    @JsonProperty
    private boolean active;
    @JsonProperty
    private LocalDate lastModified;

    public String getCgId() {
        return cgId;
    }

    public void setCgId(String cgId) {
        this.cgId = cgId;
    }

    public Map<String, Service> getReferredServices() {
        return referredServices;
    }

    public void setReferredServices(Map<String, Service> referredServices) {
        this.referredServices = referredServices;
    }

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

    public Map<String, String> scheduleDataMap() {
        Map<String, String> params = new HashMap<String, String>();
        //Add data that are required
        return params;
    }

    public Map<String, Object> appointmentDataMap() {
        Map<String, Object> params = new HashMap<String, Object>();
        //Add data that are required
        return params;
    }

    public String getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public String getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(String signedDate) {
        this.signedDate = signedDate;
    }

    public List<String> referredServiceCodes() {
        List<String> serviceCodes = new ArrayList<String>();

        List<ServiceType> serviceTypes = extract(filter(having(on(Service.class).isReferred()), referredServices.values()), on(Service.class).getServiceType());
        //extract does not work on Enum, so need the below loop
        for (ServiceType serviceType : serviceTypes) {
            serviceCodes.add(serviceType.getCode());
        }

        return sort(serviceCodes, on(String.class));
    }

    public WindowType window() {
        if (DateUtil.today().toDate().before(DateUtils.getDate(serviceDate)))
            return WindowType.UPCOMING;
        else if (DateUtil.today().toDate().after(DateUtils.getDate(serviceDate)))
            return WindowType.LATE;
        else
            return WindowType.DUE;
    }

    public boolean fullfilled() {
        for (Service service : referredServices.values())
            if (service.isReferred() && !service.isProvided())
                return false;
        return true;
    }

    public boolean anyServiceReceived() {
        for (Service service : referredServices.values()) {
            if (service.isProvided())
                return true;
        }
        return false;
    }
}
