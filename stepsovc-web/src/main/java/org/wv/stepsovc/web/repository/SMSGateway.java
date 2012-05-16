package org.wv.stepsovc.web.repository;

import org.motechproject.sms.api.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SMSGateway {

    SmsService smsService;

    @Autowired
    public SMSGateway(SmsService smsService) {
        this.smsService = smsService;
    }

    public void send(List<String> mobileNumber, String payload) {
        smsService.sendSMS(mobileNumber, payload);
    }
}
