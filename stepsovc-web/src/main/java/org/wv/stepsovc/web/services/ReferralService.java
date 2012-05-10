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
import org.wv.stepsovc.web.repository.AllReferrals;
import org.wv.stepsovc.web.request.StepsovcCase;

import java.util.List;

public class ReferralService {

    private static Logger logger = Logger.getLogger(BeneficiaryService.class);

    @Autowired
    private AllReferrals allReferrals;

    @Autowired
    private CommcareGateway commcareGateway;

    private String COMMCARE_URL;

    public ReferralService(String COMMCARE_URL) {
        this.COMMCARE_URL = COMMCARE_URL;
    }

    public void addNewReferral(StepsovcCase stepsovcCase) {
        logger.info("Handling new referral");
        Referral oldActiveReferral = allReferrals.findActiveReferral(stepsovcCase.getBeneficiary_code());
        if(oldActiveReferral != null) {
            oldActiveReferral.setActive(false);
            allReferrals.update(oldActiveReferral);
        }

        Referral newReferral = new ReferralMapper().map(stepsovcCase);
        newReferral.setActive(true);

        allReferrals.add(newReferral);

        assignToFacility(stepsovcCase);
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

        if(stepsovcCase.getService_provider() != null && !"".equals(stepsovcCase.getService_provider().trim())) {
            assignToFacility(stepsovcCase);
        } else {
            removeFromCurrentFacility(stepsovcCase);
        }
    }

    public void updateReferralsServiceDate(String facilityId, String fromDateStr, String toDateStr, String nextAvailableDate) {
        LocalDate fromDate = DateUtils.getLocalDate(fromDateStr);
        LocalDate toDate = DateUtils.getLocalDate(toDateStr);

        while(!fromDate.isAfter(toDate)) {
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
        String groupId = commcareGateway.getGroupId(stepsovcCase.getService_provider());
        String ownerId = stepsovcCase.getUser_id() + "," + groupId;
        stepsovcCase.setOwner_id(ownerId);
        updateReferralOwner(stepsovcCase);
    }

    void updateReferralOwner(StepsovcCase stepsovcCase) {
        BeneficiaryFormRequest beneficiaryFormRequest = new BeneficiaryMapper().createFormRequest(stepsovcCase);
        commcareGateway.updateReferralOwner(COMMCARE_URL, beneficiaryFormRequest);
    }
}
