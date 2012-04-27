package org.wv.stepsovc.web.mapper;

import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.request.BeneficiaryCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReferralMapper {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Referral map(BeneficiaryCase beneficiaryCase) throws ParseException {
        Referral newReferral = new Referral();
        newReferral.setBeneficiaryCode(beneficiaryCase.getBeneficiary_code());
        newReferral.setFacilityId(beneficiaryCase.getService_provider());
        newReferral.setServiceDate(dateFormat.parse(beneficiaryCase.getService_date()));
        newReferral.setFollowupDate(beneficiaryCase.getFollowup_date() == null ? null : dateFormat.parse(beneficiaryCase.getFollowup_date()));
        newReferral.setVisitDate(dateFormat.parse(beneficiaryCase.getVisit_date()));
        newReferral.setFollowupRequired(beneficiaryCase.getFollowup_required());
        newReferral.setArtAdherenceCounseling(beneficiaryCase.getArt_adherence_counseling());
        newReferral.setCondoms(beneficiaryCase.getCondoms());
        newReferral.setEndOfLifeHospice(beneficiaryCase.getEnd_of_life_hospice());
        newReferral.setFamilyPlanning(beneficiaryCase.getFamily_planning());
        newReferral.setHivCounseling(beneficiaryCase.getHiv_counseling());
        newReferral.setOtherHealthServices(beneficiaryCase.getOther_health_services());
        newReferral.setNewDiagnosis(beneficiaryCase.getNew_diagnosis());
        newReferral.setPainManagement(beneficiaryCase.getPain_management());
        newReferral.setPmtct(beneficiaryCase.getPmtct());
        newReferral.setSexuallyTransmittedInfection(beneficiaryCase.getSexually_transmitted_infection());
        return newReferral;
    }
}
