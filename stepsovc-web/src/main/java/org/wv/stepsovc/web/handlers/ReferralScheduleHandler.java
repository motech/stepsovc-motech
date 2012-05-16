package org.wv.stepsovc.web.handlers;

import org.motechproject.appointments.api.EventKeys;
import org.motechproject.model.MotechEvent;
import org.motechproject.server.event.annotations.MotechListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.web.repository.AllFacilities;
import org.wv.stepsovc.web.repository.AllReferrals;
import org.wv.stepsovc.web.repository.SMSGateway;

@Component
public class ReferralScheduleHandler {

    private Logger logger = LoggerFactory.getLogger(ReferralScheduleHandler.class);
    SMSGateway smsGateway;
    private AllReferrals allReferrals;
    private AllFacilities allFacilities;

    @Autowired
    public ReferralScheduleHandler(SMSGateway gateway, AllReferrals referrals, AllFacilities allFacilities) {
        this.smsGateway = gateway;
        this.allReferrals = referrals;
        this.allFacilities = allFacilities;
    }

    @MotechListener(subjects = {EventKeys.APPOINTMENT_REMINDER_EVENT_SUBJECT})
    public void handleAlert(MotechEvent motechEvent) {
        try {
            // Todo: Working on story
            //sendSMSToFacilityForAnAppointment(REFERRAL_ALERT, motechEvent);
        } catch (Exception e) {
            logger.error("<Appointment Alert Exception>: Encountered error while sending alert: ", e);
            throw new EventHandlerException(motechEvent, e);
        }
    }

    /*protected void sendSMSToFacilityForAnAppointment(String smsKey, MotechEvent motechEvent) {

        Map<String, Object> parameters = motechEvent.getParameters();
        Referral referral = allReferrals.findActiveByOvcId((String) parameters.get(EventKeys.EXTERNAL_ID_KEY));
        Facility facility = allFacilities.findFacilityByCode(referral.getFacilityCode());

        List<String> phoneNumbers = facility.getPhoneNumbers();
        if (phoneNumbers.size() == 0) {
            logger.warn("No Phone Numbers to send SMS.");
        }
        smsGateway.send(phoneNumbers, null);
    }*/
}
