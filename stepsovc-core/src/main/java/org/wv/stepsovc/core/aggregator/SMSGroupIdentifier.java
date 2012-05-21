package org.wv.stepsovc.core.aggregator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.Serializable;

public class SMSGroupIdentifier implements Serializable {

    public static final String DATE_FORMAT = "yyyy-MM-dd-HH:mm";
    private String phoneNumber;
    private DateTime deliveryDate;
    private String groupKey;

    public SMSGroupIdentifier(String phoneNumber, DateTime deliveryTime, String groupKey) {
        this.phoneNumber = phoneNumber;
        this.groupKey = groupKey;
        this.deliveryDate = deliveryTime;
    }

    @Override
    public String toString() {
        return identifier();
    }

    public String identifier() {
        return groupKey + "|"+ phoneNumber + "|" + deliveryDate.toString(DateTimeFormat.forPattern(DATE_FORMAT));
    }
}

