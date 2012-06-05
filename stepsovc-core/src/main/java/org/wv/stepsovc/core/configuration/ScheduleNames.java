package org.wv.stepsovc.core.configuration;

public enum ScheduleNames {
    REFERRAL("Referral");

    public String getName() {
        return name;
    }

    private String name;

    ScheduleNames(String name) {
        this.name = name;
    }
}
