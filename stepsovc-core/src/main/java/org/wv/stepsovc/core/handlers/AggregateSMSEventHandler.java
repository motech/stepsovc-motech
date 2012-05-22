package org.wv.stepsovc.core.handlers;

import ch.lambdaj.Lambda;
import org.motechproject.aggregator.aggregation.AggregateMotechEvent;
import org.motechproject.cmslite.api.model.StringContent;
import org.motechproject.cmslite.api.service.CMSLiteService;
import org.motechproject.model.MotechEvent;
import org.motechproject.server.event.annotations.MotechListener;
import org.motechproject.sms.api.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.aggregator.SMSMessage;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static ch.lambdaj.Lambda.joinFrom;
import static ch.lambdaj.Lambda.on;
import static java.lang.String.format;

@Component
public class AggregateSMSEventHandler {

    @Autowired
    CMSLiteService cmsLiteService;
    @Qualifier("smsServiceImpl")
    @Autowired
    SmsService smsService;

    private Logger logger = LoggerFactory.getLogger(ReferralScheduleHandler.class);

    @MotechListener(subjects = {AggregateMotechEvent.SUBJECT})
    public void handleAggregatedSMSByIdentifier(MotechEvent event) {
        try {
            final List<SMSMessage> smsMessages = (List<SMSMessage>) event.getParameters().get(AggregateMotechEvent.VALUES_KEY);
            if (smsMessages.size() > 0)
                aggregateForFacility(smsMessages);
        } catch (Exception e) {
            throw new RuntimeException("problem in handling aggregate sms", e);
        }
    }

    private void aggregateForFacility(List<SMSMessage> smsMessages) throws Exception {

        StringContent template = cmsLiteService.getStringContent(Locale.ENGLISH.getLanguage(), smsMessages.get(0).group());
        // todo : if sms content is small, this is fine, otherwise added a sortable identifier to SMSMessage
        List<SMSMessage> sortedSMSes = Lambda.sort(smsMessages, on(SMSMessage.class).content());
        String allSMSes = joinFrom(sortedSMSes, SMSMessage.class, ", ").content();
        logger.info("Aggregated Sms : "+allSMSes);
        smsService.sendSMS(smsMessages.get(0).phoneNumber(), format(template.getValue(), allSMSes));
    }


    Comparator<String> alphabeticalOrder = new Comparator<String>() {
        @Override
        public int compare(String s, String s1) {
            return s.compareTo(s1);
        }
    };
}
