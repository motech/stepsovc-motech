package org.wv.stepsovc.core.aggregator;

import java.io.Serializable;

public class SMSGroup implements Serializable {

    private String serviceName;
    private String subGroup;
    private RecipientType recipientType;

    public SMSGroup(String serviceName, String subGroup, RecipientType recipientType) {
        this.serviceName = serviceName;
        this.subGroup = subGroup;
        this.recipientType = recipientType;
    }

    public String key() {
        return "G-" + recipientType.name() + "-" + this.serviceName + "-" + subGroup;
    }
}
