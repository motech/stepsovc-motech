package org.wv.stepsovc.core.request;

public enum BeneficiaryCaseUpdateType {
    BENEFICIARY_REGISTRATION("beneficiary_registration"),
    NEW_REFERRAL("new_referral"),
    UPDATE_SERVICE("update_service"),
    UPDATE_REFERRAL("update_referral"),
    CUSTOM_UPDATE("custom_update");

    private String type;

    BeneficiaryCaseUpdateType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
