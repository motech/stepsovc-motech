package org.wv.stepsovc.core.aggregator;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.motechproject.util.DateUtil.newDateTime;

public class SMSGroupIdentifierTest {

    @Test
    public void shouldCreateGroupIdByPhoneNumberDeliveryTimeAndGroupKey() {
        final DateTime deliveryTime = newDateTime(2012, 4, 2, 23, 2, 0);
        final String phoneNumber = "087654321";
        final String groupKey = "G-FACILITY-Referral";

        assertThat(new SMSGroupIdentifier(phoneNumber, deliveryTime, groupKey).identifier()
                , is("G-FACILITY-Referral|087654321|2012-04-02-23:02"));
        assertThat(new SMSGroupIdentifier(phoneNumber, newDateTime(2012, 4, 2, 11, 2, 0), groupKey).identifier()
                , is("G-FACILITY-Referral|087654321|2012-04-02-11:02"));
    }
}
