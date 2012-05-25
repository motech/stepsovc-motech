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
import org.wv.stepsovc.core.domain.*;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.repository.AllFacilities;
import org.wv.stepsovc.core.repository.AllReferrals;
import org.wv.stepsovc.core.utils.DateUtils;

import java.text.SimpleDateFormat;
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
            Map<String, Object> parameters = motechEvent.getParameters();
            Referral referral = allReferrals.findActiveByOvcId((String) parameters.get(EventKeys.EXTERNAL_ID_KEY));
            Beneficiary beneficiary = allBeneficiaries.findBeneficiary(referral.getBeneficiaryCode());
            sendAggregatedSMSToFacility(referral, beneficiary);
            if (referral.window() == WindowType.DUE)
                sendInstanceSMSToCaregiver(referral, beneficiary);
        } catch (Exception e) {
            logger.debug("<Appointment Alert Exception>: Encountered error while sending alert: ", e);
            throw new EventHandlerException(motechEvent, e);
        }
    }


    private void sendAggregatedSMSToFacility(Referral referral, Beneficiary beneficiary) throws ContentNotFoundException {
        Facility facility = allFacilities.findFacilityByCode(referral.getFacilityCode());
        List<String> phoneNumbers = facility.getPhoneNumbers();
        if (isEmpty(phoneNumbers)) {
            logger.error("No Phone Numbers to send SMS.");
        } else {
            final DateTime now = newDateTime(new LocalDate(), 7, 30, 0);
            StringContent smsTemplate = cmsLiteService.getStringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.REFERRAL_ALERT);
            String smsContent = format(smsTemplate.getValue(), beneficiary.getName(), beneficiary.getCode(), join(referral.referredServiceCodes(), ","));
            logger.info("Sms Content : " + smsContent+ "dateTime :" +now);
            String patientDueDate = new SimpleDateFormat("dd-MMM-yyyy").format(DateUtils.getDate(referral.getServiceDate()));
            for (String phoneNumber : phoneNumbers) {
                eventAggregationGateway.dispatch(new SMSMessage(now, phoneNumber, smsContent, group(Referral.VISIT_NAME, referral.window().name(), FACILITY).key(), patientDueDate));
            }
        }
    }

    private void sendInstanceSMSToCaregiver(Referral referral, Beneficiary beneficiary) {

    }
}
