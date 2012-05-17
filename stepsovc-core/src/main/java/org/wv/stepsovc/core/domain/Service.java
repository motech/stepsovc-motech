package org.wv.stepsovc.core.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class Service {

    @JsonProperty
    private boolean referred;

    @JsonProperty
    private boolean provided;

    @JsonProperty
    private String reason;

    public Service(boolean referred) {
        this.referred = referred;
    }

    public Service() {

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
        this.provided = provided;
    }

    public String getReason() {
        return reason;
    }

    public Service setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
