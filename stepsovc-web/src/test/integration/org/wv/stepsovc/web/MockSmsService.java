package org.wv.stepsovc.web;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.motechproject.sms.api.service.SmsService;

import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;

public class MockSmsService implements SmsService {

    private Logger logger = Logger.getLogger(this.getClass());

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
