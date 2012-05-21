package org.wv.stepsovc.core.aggregator;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.motechproject.util.DateUtil.newDateTime;
import static org.motechproject.util.DateUtil.now;

public class SMSAggregationHandlerTest {

    SMSAggregationHandler smsAggregationHandler = new SMSAggregationHandler();

    @Test
    public void shouldGroupByPhoneNumberDeliveryTimeAndGroupKey() {
        final DateTime deliveryTime = newDateTime(2012, 4, 2, 23, 2, 0);
        final String phoneNumber = "087654321";
        final String groupKey = "G-FACILITY-Referral";
        assertThat(smsAggregationHandler.groupId(new SMSMessage(deliveryTime, phoneNumber, "c1", groupKey))
                , is("G-FACILITY-Referral|087654321|2012-04-02-23:02"));
        assertThat(smsAggregationHandler.groupId(new SMSMessage(newDateTime(2012, 4, 2, 11, 2, 0), phoneNumber, "c2", groupKey))
                , is("G-FACILITY-Referral|087654321|2012-04-02-11:02"));
    }

    @Test
    public void shouldReturnCanBeDispatchedBasedOnDeliveryTime() {
        final DateTime deliveryTime = now().minusSeconds(1);
        final List<SMSMessage> smsMessages = asList(new SMSMessage(deliveryTime, "phoneNumber", "c1", "groupKey"),
                new SMSMessage(deliveryTime, "phoneNumber", "c1", "g2"));

        assertTrue(smsAggregationHandler.canBeDispatched(smsMessages));
        assertFalse(smsAggregationHandler.canBeDispatched(asList(new SMSMessage(now().plusHours(1), "pN", "c1", "g1"))));
    }
}
