package org.wv.stepsovc.core.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class Service {

    @JsonProperty
    private ServiceType serviceType;

    @JsonProperty
    private boolean referred;

    @JsonProperty
    private boolean provided;

    @JsonProperty
    private String reason;

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Service() {

    }

    public Service(boolean referred, boolean provided, ServiceType serviceType) {
        this.provided = provided;
        this.referred = referred;
        this.serviceType = serviceType;
    }

    public boolean isReferred() {
        return referred;
    }

    public void setReferred(boolean referred) {
        this.referred = referred;
    }

    public boolean isProvided() {
        return provided;
    }

    public void setProvided(boolean provided) {
        this.provided = this.provided || provided;
    }

    public String getReason() {
        return reason;
    }

    public Service setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
