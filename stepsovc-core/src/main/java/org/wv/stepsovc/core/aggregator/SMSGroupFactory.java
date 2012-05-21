package org.wv.stepsovc.core.aggregator;

public class SMSGroupFactory {

    public static SMSGroup group(String serviceName, String subGroup, RecipientType recipientType) {
        return new SMSGroup(serviceName, subGroup, recipientType);
    }
}
