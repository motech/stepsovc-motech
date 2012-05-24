package org.wv.stepsovc.core.services;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.mapper.BeneficiaryMapper;
import org.wv.stepsovc.core.mapper.ReferralMapper;
import org.wv.stepsovc.core.repository.AllAppointments;
import org.wv.stepsovc.core.repository.AllReferrals;
import org.wv.stepsovc.core.request.StepsovcCase;
import org.wv.stepsovc.core.utils.DateUtils;
import org.wv.stepsovc.core.vo.FacilityAvailability;

import java.util.List;

import static org.wv.stepsovc.core.factories.VisitRequestFactory.createVisitRequestForReferral;
import static org.wv.stepsovc.core.utils.DateUtils.getDateTime;

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

    public void addNewReferral(StepsovcCase stepsovcCase) {
        logger.info("Handling new referral");
        inactivateOldReferral(stepsovcCase);

        Referral newReferral = new ReferralMapper().map(stepsovcCase);
        checkForAvailableDate(newReferral);
        updateReferralOwner(stepsovcCase, stepsovcCase.getFacility_code());

        allAppointments.add(newReferral.getOvcId(), createVisitRequestForReferral(newReferral.appointmentDataMap(), getDateTime(newReferral.getServiceDate())));
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
            allAppointments.remove(oldActiveReferral.getOvcId());
            oldActiveReferral.setActive(false);
            allReferrals.update(oldActiveReferral);
        }
    }

    public void updateNotAvailedReasons(StepsovcCase stepsovcCase) {
        logger.info("Handling update referral");
        Referral existingReferral = allReferrals.findActiveReferral(stepsovcCase.getBeneficiary_code());
        allAppointments.remove(existingReferral.getOvcId());
        allReferrals.update(new ReferralMapper().updateReferral(existingReferral, stepsovcCase));
    }

    public void updateAvailedServices(StepsovcCase stepsovcCase) {
        logger.info("Handling update service");
        Referral existingReferral = allReferrals.findActiveReferral(stepsovcCase.getBeneficiary_code());

        Referral referral = new ReferralMapper().updateServices(existingReferral, stepsovcCase);

        allAppointments.remove(referral.getOvcId());

        if (stepsovcCase.getFacility_code() != null && !"".equals(stepsovcCase.getFacility_code().trim())) {
            checkForAvailableDate(referral);
            allAppointments.add(referral.getOvcId(), createVisitRequestForReferral(referral.appointmentDataMap(), getDateTime(referral.getServiceDate())));
            updateReferralOwner(stepsovcCase, stepsovcCase.getFacility_code());
        } else {
            updateReferralOwner(stepsovcCase, null);
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
            allAppointments.add(referral.getOvcId(), createVisitRequestForReferral(referral.appointmentDataMap(), getDateTime(referral.getServiceDate())));
        }
    }

    public void updateReferralOwner(StepsovcCase stepsovcCase, String facilityCode) {
        commcareGateway.updateCaseOwner(new BeneficiaryMapper().createFormRequest(stepsovcCase), stepsovcCase.getUser_id(), facilityCode);
    }
}
