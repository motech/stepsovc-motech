package org.wv.stepsovc.core.services;

import org.motechproject.cmslite.api.model.ContentNotFoundException;
import org.motechproject.cmslite.api.model.StringContent;
import org.motechproject.cmslite.api.service.CMSLiteService;
import org.motechproject.sms.api.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wv.stepsovc.core.domain.Beneficiary;
import org.wv.stepsovc.core.domain.Facility;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.SmsTemplateKeys;
import org.wv.stepsovc.core.repository.AllAppointments;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.repository.AllFacilities;

import java.util.List;
import java.util.Locale;

import static ch.lambdaj.Lambda.join;
import static org.wv.stepsovc.core.factories.VisitRequestFactory.createVisitRequestForReferral;
import static org.wv.stepsovc.core.utils.DateUtils.getDateTime;

@Service
public class ReferralAlertService {

    @Autowired
    private AllAppointments allAppointments;
    @Autowired
    private SmsService smsService;
    @Autowired
    private AllFacilities allFacilities;
    @Autowired
    private CMSLiteService cmsLiteService;
    @Autowired
    private AllBeneficiaries allBeneficiaries;


    public void newReferralAlert(Referral referral) {
        allAppointments.add(referral.getOvcId(), createVisitRequestForReferral(referral.appointmentDataMap(), getDateTime(referral.getServiceDate())));
        sendInstantSMSToFacility(referral, allFacilities.findFacilityByCode(referral.getFacilityCode()));
    }

    private void sendInstantSMSToFacility(Referral referral, Facility facility) {
        if(facility != null && facility.getPhoneNumbers() != null) {
            List<String> phoneNumbers = facility.getPhoneNumbers();
            String message = null;
            try {
                StringContent template = cmsLiteService.getStringContent(Locale.ENGLISH.getLanguage(), SmsTemplateKeys.IMPENDING_REFERRAL);
                Beneficiary beneficiary = allBeneficiaries.findBeneficiary(referral.getBeneficiaryCode());
                message = String.format(template.getValue(), beneficiary.getName(), beneficiary.getCode(), facility.getFacilityName(), referral.getServiceDate());
                smsService.sendSMS(phoneNumbers,message);
            } catch (ContentNotFoundException e) {
            }
        }
    }

    public void removeAlertSchedules(Referral referral) {
        allAppointments.remove(referral.getOvcId());
    }
}
