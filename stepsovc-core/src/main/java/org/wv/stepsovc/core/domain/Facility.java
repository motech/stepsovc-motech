package org.wv.stepsovc.core.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

import java.util.List;

@TypeDiscriminator("doc.type == 'Facility'")
public class Facility extends MotechBaseDataObject {
    @JsonProperty
    private String facilityCode;
    @JsonProperty
    private String facilityName;
    @JsonProperty
    private List<ServiceUnavailability> serviceUnavailabilities;
    @JsonProperty
    private List<String> phoneNumbers;
    @JsonProperty
    private String district;
    @JsonProperty
    private String constituency;
    @JsonProperty
    private String ward;
    @JsonProperty
    private String village;

    public Facility() {

    }

    public Facility(String facilityCode, String facilityName, List<ServiceUnavailability> serviceUnavailabilities, List<String> phoneNumbers) {
        this.facilityCode = facilityCode;
        this.facilityName = facilityName;
        this.serviceUnavailabilities = serviceUnavailabilities;
        this.phoneNumbers = phoneNumbers;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public List<ServiceUnavailability> getServiceUnavailabilities() {
        return serviceUnavailabilities;
    }

    public void setServiceUnavailabilities(List<ServiceUnavailability> serviceUnavailabilities) {
        this.serviceUnavailabilities = serviceUnavailabilities;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public Facility setPhoneNumber(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }
}
