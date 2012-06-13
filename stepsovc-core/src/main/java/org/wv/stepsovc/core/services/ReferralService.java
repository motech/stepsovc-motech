package org.wv.stepsovc.core.services;

import org.apache.commons.lang.StringUtils;
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

import java.util.ArrayList;
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
    private StepsovcScheduleService stepsovcScheduleService;

    public void addNewReferral(StepsovcCase stepsovcCase) {
        logger.info("Handling new referral for " + stepsovcCase.getBeneficiary_code());
        inactivateOldReferral(stepsovcCase);

        Referral newReferral = new ReferralMapper().map(stepsovcCase);
        checkForAvailableDate(newReferral);
        commcareGateway.addGroupOwnership(new BeneficiaryMapper().createFormRequest(stepsovcCase), stepsovcCase.getFacility_code());

        stepsovcScheduleService.scheduleNewReferral(newReferral);
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
            stepsovcScheduleService.unscheduleReferral(oldActiveReferral.getOvcId());
            stepsovcScheduleService.unscheduleDefaultment(oldActiveReferral.getOvcId());
            stepsovcScheduleService.unscheduleFollowUpVisit(oldActiveReferral.getOvcId());
            oldActiveReferral.setActive(false);
            allReferrals.update(oldActiveReferral);
        }
    }

    public void updateNotAvailedReasons(StepsovcCase stepsovcCase) {
        logger.info("Handling update referral for " + stepsovcCase.getBeneficiary_code());
        Referral existingReferral = allReferrals.findActiveReferral(stepsovcCase.getBeneficiary_code());
        stepsovcScheduleService.unscheduleReferral(existingReferral.getOvcId());
        allReferrals.update(new ReferralMapper().updateReferral(existingReferral, stepsovcCase));
    }

    public void updateAvailedServices(StepsovcCase stepsovcCase) {
        logger.info("Handling update service for " + stepsovcCase.getBeneficiary_code());
        Referral existingReferral = allReferrals.findActiveReferral(stepsovcCase.getBeneficiary_code());
        String existingFacilityCode = existingReferral.getFacilityCode();
        existingReferral = new ReferralMapper().updateServices(existingReferral, stepsovcCase);
        stepsovcScheduleService.unscheduleReferral(existingReferral.getOvcId());
        checkForReferralForward(stepsovcCase, existingFacilityCode, existingReferral);
        allReferrals.update(existingReferral);
    }

    private void checkForReferralForward(StepsovcCase stepsovcCase, String existingFacilityCode, Referral referral) {
        if (StringUtils.isNotEmpty(stepsovcCase.getFacility_code())) {
            stepsovcScheduleService.unscheduleDefaultment(referral.getOvcId());
            checkForAvailableDate(referral);
            commcareGateway.addGroupOwnership(new BeneficiaryMapper().createFormRequest(stepsovcCase), stepsovcCase.getFacility_code());
            stepsovcScheduleService.scheduleNewReferral(referral);
        } else {
            if (referral.fullfilled())
                stepsovcScheduleService.unscheduleDefaultment(referral.getOvcId());
            commcareGateway.removeGroupOwnership(new BeneficiaryMapper().createFormRequest(stepsovcCase), existingFacilityCode);
        }
    }

    public List<Referral> updateReferralsServiceDate(String facilityCode, String fromDateStr, String toDateStr, String nextAvailableDate) {
        LocalDate fromDate = DateUtils.getLocalDate(fromDateStr);
        LocalDate toDate = DateUtils.getLocalDate(toDateStr);
        List<Referral> updatedReferrals = new ArrayList<Referral>();

        while (!fromDate.isAfter(toDate)) {
            List<Referral> referrals = allReferrals.findActiveReferrals(facilityCode, DateUtils.getFormattedDate(fromDate.toDate()));
            updateReferrals(nextAvailableDate, referrals);
            fromDate = fromDate.plusDays(1);
            updatedReferrals.addAll(referrals);
        }
        return updatedReferrals;
    }

    private void updateReferrals(String nextAvailableDate, List<Referral> referrals) {
        for (Referral referral : referrals) {
            stepsovcScheduleService.unscheduleReferral(referral.getOvcId());
            allReferrals.update(referral.setServiceDate(nextAvailableDate));
            stepsovcScheduleService.scheduleNewReferral(referral);
        }
    }
}
