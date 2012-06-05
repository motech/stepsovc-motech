package org.wv.stepsovc.core.services;

import org.apache.log4j.Logger;
import org.motechproject.cmslite.api.model.ContentNotFoundException;
import org.motechproject.cmslite.api.model.StringContent;
import org.motechproject.cmslite.api.service.CMSLiteService;
import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.motechproject.sms.api.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wv.stepsovc.core.configuration.ScheduleNames;
import org.wv.stepsovc.core.domain.Beneficiary;
import org.wv.stepsovc.core.domain.Facility;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.SmsTemplateKeys;
import org.wv.stepsovc.core.mapper.ScheduleEnrollmentMapper;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.repository.AllFacilities;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class ReferralAlertService {

    @Autowired
    private SmsService smsService;
    @Autowired
    private AllFacilities allFacilities;
    @Autowired
    private CMSLiteService cmsLiteService;
    @Autowired
    private AllBeneficiaries allBeneficiaries;
    @Autowired
    private ScheduleTrackingService scheduleTrackingService;

    private Logger logger = Logger.getLogger(this.getClass());

    public void newReferralAlert(Referral referral) {
        scheduleTrackingService.enroll(new ScheduleEnrollmentMapper().map(referral));
        sendInstantSMSToFacility(referral, allFacilities.findFacilityByCode(referral.getFacilityCode()));
    }

    private void sendInstantSMSToFacility(Referral referral, Facility facility) {
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

    public void removeAlertSchedules(Referral referral) {
        scheduleTrackingService.unenroll(referral.getOvcId(), Arrays.asList(ScheduleNames.REFERRAL.getName()));
    }
}
