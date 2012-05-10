package org.wv.stepsovc.web.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class ServiceUnavailability {
    @JsonProperty
    private String unavailableReason;
    @JsonProperty
    private String fromDate;
    @JsonProperty
    private String toDate;

    public ServiceUnavailability() {
    }

    public ServiceUnavailability(String unavailableReason, String fromDate, String toDate) {
        this.unavailableReason = unavailableReason;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getUnavailableReason() {
        return unavailableReason;
    }

    public void setUnavailableReason(String unavailableReason) {
        this.unavailableReason = unavailableReason;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}
