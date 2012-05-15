package org.wv.stepsovc.web.services;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.utils.DateUtils;
import org.wv.stepsovc.vo.BeneficiaryFormRequest;
import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.mapper.BeneficiaryMapper;
import org.wv.stepsovc.web.mapper.ReferralMapper;
import org.wv.stepsovc.web.repository.AllAppointments;
import org.wv.stepsovc.web.repository.AllReferrals;
import org.wv.stepsovc.web.request.StepsovcCase;
import org.wv.stepsovc.web.vo.FacilityAvailability;

import java.util.List;

public class ReferralService {

    private static Logger logger = Logger.getLogger(BeneficiaryService.class);

    @Autowired
    private AllReferrals allReferrals;

    @Autowired
    private CommcareGateway commcareGateway;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private AllAppointments allAppointments;

    private String COMMCARE_URL;

    public ReferralService(String COMMCARE_URL) {
        this.COMMCARE_URL = COMMCARE_URL;
    }

    public void addNewReferral(StepsovcCase stepsovcCase) {
        logger.info("Handling new referral");
        inactivateOldReferral(stepsovcCase); // Todo : remove existing appointments

        Referral newReferral = new ReferralMapper().map(stepsovcCase);
        checkForAvailableDate(newReferral);
        assignToFacility(stepsovcCase);

        allAppointments.scheduleNewReferral(newReferral.getOvcId(), newReferral.appointmentDataMap(), DateUtils.getDateTime(newReferral.getServiceDate()));
        allReferrals.add(newReferral);
    }

    private void checkForAvailableDate(Referral referral) {
        FacilityAvailability facilityAvailability = facilityService.getFacilityAvailability(referral.getFacilityCode(), referral.getServiceDate());
        if (!facilityAvailability.isAvailable()) {
            referral.setServiceDate(facilityAvailability.getNextAvailableDate());
        }
    }

    private void inactivateOldReferral(StepsovcCase stepsovcCase) {
        Referral oldActiveReferral = allReferrals.findActiveReferral(stepsovcCase.getBeneficiary_code());
        if (oldActiveReferral != null) {
            oldActiveReferral.setActive(false);
            allReferrals.update(oldActiveReferral);
        }
    }

    public void updateNotAvailedReasons(StepsovcCase stepsovcCase) {
        logger.info("Handling update referral");
        Referral existingReferral = allReferrals.findActiveReferral(stepsovcCase.getBeneficiary_code());

        allReferrals.update(new ReferralMapper().updateReferral(existingReferral, stepsovcCase));
    }

    public void updateAvailedServices(StepsovcCase stepsovcCase) {
        logger.info("Handling update service");
        Referral existingReferral = allReferrals.findActiveReferral(stepsovcCase.getBeneficiary_code());

        allReferrals.update(new ReferralMapper().updateServices(existingReferral, stepsovcCase));
        checkForNewReferral(stepsovcCase);
    }

    private void checkForNewReferral(StepsovcCase stepsovcCase) {
        if (stepsovcCase.getFacility_code() != null && !"".equals(stepsovcCase.getFacility_code().trim())) {
            assignToFacility(stepsovcCase);
        } else {
            removeFromCurrentFacility(stepsovcCase);
        }
    }

    public void updateReferralsServiceDate(String facilityId, String fromDateStr, String toDateStr, String nextAvailableDate) {
        LocalDate fromDate = DateUtils.getLocalDate(fromDateStr);
        LocalDate toDate = DateUtils.getLocalDate(toDateStr);

        while (!fromDate.isAfter(toDate)) {
            List<Referral> referrals = allReferrals.findActiveReferrals(facilityId, DateUtils.getFormattedDate(fromDate.toDate()));
            updateReferrals(nextAvailableDate, referrals);
            fromDate = fromDate.plusDays(1);
        }
    }

    private void updateReferrals(String nextAvailableDate, List<Referral> referrals) {
        for (Referral referral : referrals) {
            referral.setServiceDate(nextAvailableDate);
            allReferrals.update(referral);
        }
    }

    void removeFromCurrentFacility(StepsovcCase stepsovcCase) {
        stepsovcCase.setOwner_id(stepsovcCase.getUser_id());
        updateReferralOwner(stepsovcCase);
    }

    void assignToFacility(StepsovcCase stepsovcCase) {
        String groupId = commcareGateway.getGroupId(stepsovcCase.getFacility_code());
        String ownerId = stepsovcCase.getUser_id() + "," + groupId;
        stepsovcCase.setOwner_id(ownerId);
        updateReferralOwner(stepsovcCase);
    }

    void updateReferralOwner(StepsovcCase stepsovcCase) {
        BeneficiaryFormRequest beneficiaryFormRequest = new BeneficiaryMapper().createFormRequest(stepsovcCase);
        commcareGateway.updateReferralOwner(COMMCARE_URL, beneficiaryFormRequest);
    }
}
