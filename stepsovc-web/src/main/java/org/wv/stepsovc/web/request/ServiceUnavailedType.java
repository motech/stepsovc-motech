package org.wv.stepsovc.web.request;

public enum ServiceUnavailedType {
    SERVICE_UNAVAILABLE("Service unavailable in the facility"),
    FACILITY_CLOSED("Facility Closed"),
    BEN_UNABLE_TO_TRAVEL("Beneficiary unable to travel"),
    BEN_FORGOT("Beneficiary forgot the appointment"),
    BEN_UNWILLING("Beneficiary unwilling to avail the service"),
    AVAILED_NOT_RECORED("Service availed but not recorded"),
    OTHER("Other");

    public String getValue() {
        return value;
    }

    private String value;

    ServiceUnavailedType(String value) {
        this.value = value;
    }
}
