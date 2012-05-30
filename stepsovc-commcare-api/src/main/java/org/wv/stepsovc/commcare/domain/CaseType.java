package org.wv.stepsovc.commcare.domain;

public enum CaseType {
    BENEFICIARY_CASE("beneficiary"),
    FACILITY_CASE("facility"),
    BENEFICIARY_OWNERSHIP_CASE("beneficiary_ownership");

    private String type;

    CaseType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
