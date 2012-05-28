package org.wv.stepsovc.core;

import org.joda.time.DateTime;
import org.motechproject.sms.api.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;

public class MockSmsService implements SmsService {

    private Logger logger = LoggerFactory.getLogger(MockSmsService.class);

    @Override
    public void sendSMS(String recipient, String message) {
        logger.info(format("sendSMS for recipient (%s), message (%s)", recipient, message));
    }

    @Override
    public void sendSMS(List<String> recipients, String message) {
        logger.info(format("sendSMS for recipients (%s), message (%s)", recipients, message));
    }

    @Override
    public void sendSMS(String recipient, String message, DateTime deliveryTime) {
        logger.info(format("sendSMS for recipient (%s), message (%s) @ delivery (%s)", recipient, message, deliveryTime));
    }

    @Override
    public void sendSMS(ArrayList<String> recipients, String message, DateTime deliveryTime) {
        logger.info(format("sendSMS for recipients (%s), message (%s) @ delivery (%s)", recipients, message, deliveryTime));
    }
}
