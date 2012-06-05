package org.wv.stepsovc.core.services;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.mapper.BeneficiaryMapper;
import org.wv.stepsovc.core.mapper.ReferralMapper;
import org.wv.stepsovc.core.repository.AllReferrals;
import org.wv.stepsovc.core.request.StepsovcCase;
import org.wv.stepsovc.core.utils.DateUtils;
import org.wv.stepsovc.core.vo.FacilityAvailability;

import java.util.List;

public class ReferralService {

    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private AllReferrals allReferrals;
    @Autowired
    private CommcareGateway commcareGateway;
    @Autowired
    private FacilityService facilityService;
    @Autowired
    private ReferralAlertService referralAlertService;

    public void addNewReferral(StepsovcCase stepsovcCase) {
        logger.info("Handling new referral");
        inactivateOldReferral(stepsovcCase);

        Referral newReferral = new ReferralMapper().map(stepsovcCase);
        checkForAvailableDate(newReferral);
        commcareGateway.addGroupOwnership(new BeneficiaryMapper().createFormRequest(stepsovcCase), stepsovcCase.getFacility_code());
        logger.info("Scheduling new referral alerts");

        referralAlertService.newReferralAlert(newReferral);
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
            referralAlertService.removeAlertSchedules(oldActiveReferral);
            oldActiveReferral.setActive(false);
            allReferrals.update(oldActiveReferral);
        }
    }

    public void updateNotAvailedReasons(StepsovcCase stepsovcCase) {
        logger.info("Handling update referral");
        Referral existingReferral = allReferrals.findActiveReferral(stepsovcCase.getBeneficiary_code());
        referralAlertService.removeAlertSchedules(existingReferral);
        allReferrals.update(new ReferralMapper().updateReferral(existingReferral, stepsovcCase));
    }

    public void updateAvailedServices(StepsovcCase stepsovcCase) {
        logger.info("Handling update service");
        Referral existingReferral = allReferrals.findActiveReferral(stepsovcCase.getBeneficiary_code());

        referralAlertService.removeAlertSchedules(existingReferral);

        Referral referral = new ReferralMapper().updateServices(existingReferral, stepsovcCase);

        if (stepsovcCase.getFacility_code() != null && !"".equals(stepsovcCase.getFacility_code().trim())) {
            checkForAvailableDate(referral);
            referralAlertService.newReferralAlert(referral);
            commcareGateway.addGroupOwnership(new BeneficiaryMapper().createFormRequest(stepsovcCase), stepsovcCase.getFacility_code());
        } else {
            commcareGateway.removeGroupOwnership(new BeneficiaryMapper().createFormRequest(stepsovcCase), stepsovcCase.getFacility_code());
        }
        allReferrals.update(referral);
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
            referralAlertService.newReferralAlert(referral);
        }
    }
}
