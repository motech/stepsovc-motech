package org.wv.stepsovc.core.handlers;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.motechproject.aggregator.inbound.EventAggregationGateway;
import org.motechproject.cmslite.api.model.ContentNotFoundException;
import org.motechproject.cmslite.api.model.StringContent;
import org.motechproject.cmslite.api.service.CMSLiteService;
import org.motechproject.model.MotechEvent;
import org.motechproject.scheduletracking.api.events.MilestoneEvent;
import org.motechproject.scheduletracking.api.events.constants.EventSubjects;
import org.motechproject.server.event.annotations.MotechListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.aggregator.SMSMessage;
import org.wv.stepsovc.core.domain.Beneficiary;
import org.wv.stepsovc.core.domain.Facility;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.SmsTemplateKeys;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.repository.AllCaregivers;
import org.wv.stepsovc.core.repository.AllFacilities;
import org.wv.stepsovc.core.repository.AllReferrals;
import org.wv.stepsovc.core.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.join;
import static org.motechproject.util.DateUtil.newDateTime;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.wv.stepsovc.core.aggregator.RecipientType.FACILITY;
import static org.wv.stepsovc.core.aggregator.SMSGroupFactory.group;

@Component
public class ReferralScheduleHandler {

    private Logger logger = Logger.getLogger(this.getClass());
    private EventAggregationGateway<SMSMessage> eventAggregationGateway;
    private AllReferrals allReferrals;
    private AllFacilities allFacilities;
    private CMSLiteService cmsLiteService;
    private AllBeneficiaries allBeneficiaries;
    private AllCaregivers allCaregivers;

    @Autowired
    public ReferralScheduleHandler(EventAggregationGateway<SMSMessage> eventAggregationGateway, CMSLiteService cmsLiteService, AllReferrals referrals, AllFacilities allFacilities, AllBeneficiaries allBeneficiaries, AllCaregivers allCaregivers) {
        this.eventAggregationGateway = eventAggregationGateway;
        this.allReferrals = referrals;
        this.allFacilities = allFacilities;
        this.cmsLiteService = cmsLiteService;
        this.allBeneficiaries = allBeneficiaries;
        this.allCaregivers = allCaregivers;
    }

    @MotechListener(subjects = {EventSubjects.MILESTONE_ALERT})
    public void handleAlert(MotechEvent motechEvent) {
        MilestoneEvent milestoneEvent = new MilestoneEvent(motechEvent);
        logger.debug(milestoneEvent.getWindowName() + " - External Id:" + milestoneEvent.getExternalId());
        try {
            Referral referral = allReferrals.findActiveByOvcId(milestoneEvent.getExternalId());
            Beneficiary beneficiary = allBeneficiaries.findBeneficiaryByCode(referral.getBeneficiaryCode());
            sendAggregatedSMSToFacility(referral, beneficiary, milestoneEvent.getWindowName());
        } catch (Exception e) {
            logger.debug("<Milestone Alert Exception>: Encountered error while sending alert: ", e);
            throw new EventHandlerException(motechEvent, e);
        }

    }

    @MotechListener(subjects = {EventSubjects.DEFAULTMENT_CAPTURE})
    public void handleDefaultmentAlert(MotechEvent motechEvent) {
        MilestoneEvent milestoneEvent = new MilestoneEvent(motechEvent);

        logger.debug("Default - External Id:" + milestoneEvent.getExternalId());

        Referral referral = allReferrals.findActiveByOvcId(milestoneEvent.getExternalId());
        Beneficiary beneficiary = allBeneficiaries.findBeneficiaryByCode(referral.getBeneficiaryCode());
        //sendInstanceSMSToCaregiver(referral,beneficiary);
    }

    private void sendAggregatedSMSToFacility(Referral referral, Beneficiary beneficiary, String window) throws ContentNotFoundException {
        Facility facility = allFacilities.findFacilityByCode(referral.getFacilityCode());
        List<String> phoneNumbers = facility.getPhoneNumbers();
        if (isEmpty(phoneNumbers)) {
            logger.error("Facility - No Phone Numbers to send SMS.");
        } else {
            final DateTime now = newDateTime(new LocalDate(), 7, 30, 0);
            StringContent smsTemplate = cmsLiteService.getStringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.REFERRAL_ALERT_WITH_SERVICE);
            String smsContent = format(smsTemplate.getValue(), beneficiary.getName(), beneficiary.getCode(), join(referral.referredServiceCodes(), ","));
            logger.info("Sms Content : " + smsContent + "dateTime :" + now);
            String patientDueDate = new SimpleDateFormat("dd-MMM-yyyy").format(DateUtils.getDate(referral.getServiceDate()));
            for (String phoneNumber : phoneNumbers) {
                eventAggregationGateway.dispatch(new SMSMessage(now, phoneNumber, smsContent, group(Referral.VISIT_NAME, window, FACILITY).key(), patientDueDate));
            }
        }
    }

//    private void sendInstanceSMSToCaregiver(Referral referral, Beneficiary beneficiary) throws ContentNotFoundException {
//        Caregiver caregiver = allCaregivers.findCaregiverById(referral.getCgId());
//        String phoneNumber = caregiver.getPhoneNumber();
//        if (StringUtil.isNullOrEmpty(phoneNumber)) {
//            logger.error("Caregiver - No Phone Numbers to send SMS.");
//        } else {
//            final DateTime now = newDateTime(new LocalDate(), 7, 30, 0);
//            StringContent smsTemplate = cmsLiteService.getStringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.REFERRAL_ALERT_WITH_SERVICE);
//            String smsContent = format(smsTemplate.getValue(), beneficiary.getName(), beneficiary.getCode(), join(referral.referredServiceCodes(), ","));
//            logger.info("Sms Content : " + smsContent + "dateTime :" + now);
//            String patientDueDate = new SimpleDateFormat("dd-MMM-yyyy").format(DateUtils.getDate(referral.getServiceDate()));
//                refe.dispatch(new SMSMessage(now, phoneNumber, smsContent, group(Referral.VISIT_NAME, , FACILITY).key(), patientDueDate));
//            }
//        }
//    }
}
