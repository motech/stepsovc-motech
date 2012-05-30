package org.wv.stepsovc.core.request;

public enum CaseUpdateType {
    BENEFICIARY_REGISTRATION("beneficiary_registration"),
    NEW_REFERRAL("new_referral"),
    UPDATE_SERVICE("update_service"),
    UPDATE_REFERRAL("update_referral"),
    FACILITY_REGISTRATION("facility_registration"),
    FACILITY_UNAVAILABILITY("facility_unavailability"),
    OWNERSHIP_REGISTRATION("ownership_registration"),
    USER_OWNERSHIP_REQUEST("user_ownership_request"),
    FACILITY_OWNERSHIP_REQUEST("facility_ownership_request");

    private String type;

    CaseUpdateType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
