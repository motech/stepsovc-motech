package org.wv.stepsovc.core.aggregator;

import org.motechproject.aggregator.aggregation.AggregationHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SMSAggregationHandler implements AggregationHandler<SMSMessage> {

    @Override
    public String groupId(SMSMessage value) {
        return new SMSGroupIdentifier(value.phoneNumber(), value.deliveryTime(), value.group()).identifier();
    }

    @Override
    public boolean canBeDispatched(List<SMSMessage> events) {
        return events.get(0).canBeDispatched();
    }
}
