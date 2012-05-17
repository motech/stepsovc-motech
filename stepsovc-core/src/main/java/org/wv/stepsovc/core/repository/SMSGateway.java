package org.wv.stepsovc.core.repository;

import org.motechproject.sms.api.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SMSGateway {

    SmsService smsService;

    @Autowired
    public SMSGateway(SmsService smsService) {
        this.smsService = smsService;
    }

    public void send(List<String> mobileNumbers, String payload) {
        smsService.sendSMS(mobileNumbers, payload);
    }
}
