package org.wv.stepsovc.web.request;

public enum CaseType {
    BENEFICIARY_CASE("beneficiary"),
    FACILITY_CASE("facility");

    private String type;

    CaseType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
