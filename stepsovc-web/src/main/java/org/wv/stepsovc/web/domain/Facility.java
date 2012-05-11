package org.wv.stepsovc.web.domain;

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

    public Facility() {

    }

    public Facility(String facilityCode, String facilityName, List<ServiceUnavailability> serviceUnavailabilities) {
        this.facilityCode = facilityCode;
        this.facilityName = facilityName;
        this.serviceUnavailabilities = serviceUnavailabilities;
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
}
