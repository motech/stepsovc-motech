package org.wv.stepsovc.core.services;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.motechproject.aggregator.inbound.EventAggregationGateway;
import org.motechproject.cmslite.api.model.ContentNotFoundException;
import org.motechproject.cmslite.api.model.StringContent;
import org.motechproject.cmslite.api.service.CMSLiteService;
import org.motechproject.model.Time;
import org.motechproject.sms.api.service.SmsService;
import org.motechproject.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wv.stepsovc.core.aggregator.SMSMessage;
import org.wv.stepsovc.core.configuration.VisitNames;
import org.wv.stepsovc.core.domain.*;
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
import static org.wv.stepsovc.core.aggregator.RecipientType.CAREGIVER;
import static org.wv.stepsovc.core.aggregator.RecipientType.FACILITY;
import static org.wv.stepsovc.core.aggregator.SMSGroupFactory.group;

@Service
public class StepsovcAlertService {

    @Autowired
    private CMSLiteService cmsLiteService;
    @Autowired
    private AllBeneficiaries allBeneficiaries;
    @Autowired
    private SmsService smsService;
    @Autowired
    private AllFacilities allFacilities;
    @Autowired
    private AllCaregivers allCaregivers;
    @Autowired
    private EventAggregationGateway<SMSMessage> eventAggregationGateway;
    @Autowired
    private AllReferrals allReferrals;

    private Time preferredAggregateTime = new Time(7, 30);
    private Logger logger = Logger.getLogger(this.getClass());

    public void sendInstantReferralAlertToFacility(Referral referral) {
        Facility facility = allFacilities.findFacilityByCode(referral.getFacilityCode());
        if (facility != null && facility.getPhoneNumbers() != null) {
            List<String> phoneNumbers = facility.getPhoneNumbers();
            String message = null;
            try {
                StringContent template = cmsLiteService.getStringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.IMPENDING_REFERRAL);
                Beneficiary beneficiary = allBeneficiaries.findBeneficiaryByCode(referral.getBeneficiaryCode());
                message = String.format(template.getValue(), beneficiary.getName(), beneficiary.getCode(), facility.getFacilityName(), referral.getServiceDate());
                smsService.sendSMS(phoneNumbers, message);
            } catch (ContentNotFoundException e) {
                logger.debug("Content for SMS not found");
            }
        }
    }

    public void sendAggregatedReferralAlertToFacility(String ovcId, String window) throws ContentNotFoundException {
        Referral referral = allReferrals.findActiveByOvcId(ovcId);
        Beneficiary beneficiary = allBeneficiaries.findBeneficiaryByCode(referral.getBeneficiaryCode());
        Facility facility = allFacilities.findFacilityByCode(referral.getFacilityCode());
        List<String> phoneNumbers = facility.getPhoneNumbers();
        if (isEmpty(phoneNumbers)) {
            logger.error("Facility - No Phone Numbers to send SMS.");
        } else {
            StringContent smsTemplate = cmsLiteService.getStringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.BENEFICIARY_WITH_SERVICES);
            String smsContent = format(smsTemplate.getValue(), beneficiary.getName(), beneficiary.getCode(), join(referral.referredServiceCodes(), ","));
            logger.info("Sms Content : " + smsContent + "dateTime :" + preferredAggregateTime);
            String patientDueDate = new SimpleDateFormat("dd-MMM-yyyy").format(DateUtils.getDate(referral.getServiceDate()));
            for (String phoneNumber : phoneNumbers) {
                eventAggregationGateway.dispatch(new SMSMessage(newDateTime(new LocalDate(), preferredAggregateTime), phoneNumber, smsContent, group(VisitNames.REFERRAL, window, FACILITY).key(), patientDueDate));
            }
        }
    }

    public void sendInstantDefaultedAlertToCaregiver(String ovcId) throws ContentNotFoundException {
        Referral referral = allReferrals.findActiveByOvcId(ovcId);
        Beneficiary beneficiary = allBeneficiaries.findBeneficiaryByCode(referral.getBeneficiaryCode());
        Caregiver caregiver = allCaregivers.findCaregiverById(referral.getCgId());
        String phoneNumber = caregiver.getPhoneNumber();
        if (StringUtil.isNullOrEmpty(phoneNumber)) {
            logger.error("Caregiver - No Phone Numbers to send SMS.");
        } else {
            StringContent smsTemplate = cmsLiteService.getStringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.DEFAULTED_REFERRAL);
            String smsContent = format(smsTemplate.getValue(), beneficiary.getName(), beneficiary.getCode(), referral.getFacilityCode(), referral.getServiceDate());
            logger.info("Sms Content : " + smsContent);
            smsService.sendSMS(phoneNumber, smsContent);
        }
    }

    public void sendFollowUpAlertToCaregiver(String ovcId) throws ContentNotFoundException {
        Referral referral = allReferrals.findActiveByOvcId(ovcId);
        Beneficiary beneficiary = allBeneficiaries.findBeneficiaryByCode(referral.getBeneficiaryCode());
        Caregiver caregiver = allCaregivers.findCaregiverById(referral.getCgId());
        String phoneNumber = caregiver.getPhoneNumber();
        StringContent smsTemplate = cmsLiteService.getStringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.BENEFICIARY_WITHOUT_SERVICES);
        String smsContent = format(smsTemplate.getValue(), beneficiary.getName(), beneficiary.getCode());
        logger.info("Sms Content : " + smsContent);

        eventAggregationGateway.dispatch(new SMSMessage(newDateTime(new LocalDate(), preferredAggregateTime), phoneNumber, smsContent, group(VisitNames.FOLLOW_UP, "", CAREGIVER).key(), null));
    }
}
