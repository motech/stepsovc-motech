package org.wv.stepsovc.core.handlers;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.motechproject.aggregator.inbound.EventAggregationGateway;
import org.motechproject.appointments.api.EventKeys;
import org.motechproject.cmslite.api.model.ContentNotFoundException;
import org.motechproject.cmslite.api.model.StringContent;
import org.motechproject.cmslite.api.service.CMSLiteService;
import org.motechproject.model.MotechEvent;
import org.motechproject.server.event.annotations.MotechListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.aggregator.SMSMessage;
import org.wv.stepsovc.core.domain.Beneficiary;
import org.wv.stepsovc.core.domain.Facility;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.SmsTemplateKeys;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.repository.AllFacilities;
import org.wv.stepsovc.core.repository.AllReferrals;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.join;
import static org.motechproject.util.DateUtil.newDateTime;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.wv.stepsovc.core.aggregator.RecipientType.FACILITY;
import static org.wv.stepsovc.core.aggregator.SMSGroupFactory.group;

@Component
public class ReferralScheduleHandler {

    private Logger logger = LoggerFactory.getLogger(ReferralScheduleHandler.class);
    private EventAggregationGateway<SMSMessage> eventAggregationGateway;
    private AllReferrals allReferrals;
    private AllFacilities allFacilities;
    private CMSLiteService cmsLiteService;
    private AllBeneficiaries allBeneficiaries;

    @Autowired
    public ReferralScheduleHandler(EventAggregationGateway<SMSMessage> eventAggregationGateway, CMSLiteService cmsLiteService, AllReferrals referrals, AllFacilities allFacilities, AllBeneficiaries allBeneficiaries) {
        this.eventAggregationGateway = eventAggregationGateway;
        this.allReferrals = referrals;
        this.allFacilities = allFacilities;
        this.cmsLiteService = cmsLiteService;
        this.allBeneficiaries = allBeneficiaries;
    }

    @MotechListener(subjects = {EventKeys.APPOINTMENT_REMINDER_EVENT_SUBJECT})
    public void handleAlert(MotechEvent motechEvent) {
        try {
            sendSMSToFacilityForAnAppointment(motechEvent);
        } catch (Exception e) {
            logger.debug("<Appointment Alert Exception>: Encountered error while sending alert: ", e);
            throw new EventHandlerException(motechEvent, e);
        }
    }

    private void sendSMSToFacilityForAnAppointment(MotechEvent motechEvent) throws ContentNotFoundException {

        Map<String, Object> parameters = motechEvent.getParameters();
        Referral referral = allReferrals.findActiveByOvcId((String) parameters.get(EventKeys.EXTERNAL_ID_KEY));
        Facility facility = allFacilities.findFacilityByCode(referral.getFacilityCode());
        Beneficiary beneficiary = allBeneficiaries.findBeneficiary(referral.getBeneficiaryCode());

        List<String> phoneNumbers = facility.getPhoneNumbers();
        if (isEmpty(phoneNumbers)) {
            logger.warn("No Phone Numbers to send SMS.");
        } else {
            for (String phoneNumber : phoneNumbers) {
                final DateTime now = newDateTime(new LocalDate(), 10, 30, 0);
                StringContent smsTemplate = cmsLiteService.getStringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.REFERRAL_ALERT);
                String smsContent = format(smsTemplate.getValue(), beneficiary.getName(), beneficiary.getCode(), join(referral.servicesReferred(), ","));
                logger.info("Sms Content : "+smsContent);
                eventAggregationGateway.dispatch(new SMSMessage(now, phoneNumber, smsContent, group(Referral.VISIT_NAME, referral.window().name(), FACILITY).key()));
            }
        }
    }
}
