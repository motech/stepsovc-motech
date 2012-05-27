package org.wv.stepsovc.core.aggregator;

import org.joda.time.DateTime;
import org.motechproject.util.DateUtil;

import java.io.Serializable;

public class SMSMessage implements Serializable {
    private DateTime deliveryTime;
    private String phoneNumber;
    private String content;
    private String groupKey;
    private String patientDueDate;

    public SMSMessage(DateTime deliveryTime, String phoneNumber, String content, String groupKey, String patientDueDate) {
        this.deliveryTime = deliveryTime;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.groupKey = groupKey;
        this.patientDueDate = patientDueDate;
    }

    public DateTime deliveryTime() {
        return deliveryTime;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public String group() {
        return groupKey;
    }

    public Boolean canBeDispatched() {
        return deliveryTime().toDate().before(DateUtil.now().toDate());
    }

    public String content() {
        return content;
    }

    public String getPatientDueDate() {
        return patientDueDate;
    }
}
