package org.wv.stepsovc.web.mapper;

import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.domain.Service;
import org.wv.stepsovc.web.request.StepsovcCase;

import java.text.SimpleDateFormat;

public class ReferralMapper {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static String REFERRED = "Referred";
    static String NOT_REFERRED = "Not Referred";
    static String SERVICE_RECEIVED = "Received";
    static String SERVICE_NOT_AVAILED = "Not Availed";

    public Referral map(StepsovcCase stepsovcCase) {
        Referral newReferral = new Referral();
        newReferral.setBeneficiaryCode(stepsovcCase.getBeneficiary_code());
        newReferral.setFacilityId(stepsovcCase.getService_provider());
        newReferral.setServiceDate(stepsovcCase.getService_date());
        newReferral.setFollowupDate(stepsovcCase.getFollowup_date());
        newReferral.setVisitDate(stepsovcCase.getVisit_date());
        newReferral.setFollowupRequired(stepsovcCase.getFollowup_required());
        newReferral.setArtAdherenceCounseling(new Service(REFERRED.equals(stepsovcCase.getArt_adherence_counseling())));
        newReferral.setCondoms(new Service(REFERRED.equals(stepsovcCase.getCondoms())));
        newReferral.setEndOfLifeHospice(new Service(REFERRED.equals(stepsovcCase.getEnd_of_life_hospice())));
        newReferral.setFamilyPlanning(new Service(REFERRED.equals(stepsovcCase.getFamily_planning())));
        newReferral.setHivCounseling(new Service(REFERRED.equals(stepsovcCase.getHiv_counseling())));
        newReferral.setOtherHealthServices(new Service(REFERRED.equals(stepsovcCase.getOther_health_services())));
        newReferral.setNewDiagnosis(new Service(REFERRED.equals(stepsovcCase.getNew_diagnosis())));
        newReferral.setPainManagement(new Service(REFERRED.equals(stepsovcCase.getPain_management())));
        newReferral.setPmtct(new Service(REFERRED.equals(stepsovcCase.getPmtct())));
        newReferral.setSexuallyTransmittedInfection(new Service(REFERRED.equals(stepsovcCase.getSexually_transmitted_infection())));

        return newReferral;
    }

    public Referral updateServices(Referral referral, StepsovcCase stepsovcCase) {
        referral.getCondoms().setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getCondoms()));
        referral.getArtAdherenceCounseling().setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getArt_adherence_counseling()));
        referral.getEndOfLifeHospice().setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getEnd_of_life_hospice()));
        referral.getFamilyPlanning().setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getFamily_planning()));
        referral.getHivCounseling().setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getHiv_counseling()));
        referral.getNewDiagnosis().setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getNew_diagnosis()));
        referral.getOtherHealthServices().setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getOther_health_services()));
        referral.getPainManagement().setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getPain_management()));
        referral.getPmtct().setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getPmtct()));
        referral.getSexuallyTransmittedInfection().setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getSexually_transmitted_infection()));
        referral.setVisitDate(stepsovcCase.getVisit_date());

        referral.setFacilityId(stepsovcCase.getService_provider());
        referral.setServiceDate(stepsovcCase.getService_date());

        return referral;
    }

    public Referral updateReferral(Referral referral, StepsovcCase stepsovcCase) {
        referral.getCondoms().setReason(stepsovcCase.getCondoms_na_reason());
        referral.getArtAdherenceCounseling().setReason(stepsovcCase.getArt_adherence_na_reason());
        referral.getEndOfLifeHospice().setReason(stepsovcCase.getEnd_of_life_hospice_na_reason());
        referral.getFamilyPlanning().setReason(stepsovcCase.getFamily_planning_na_reason());
        referral.getHivCounseling().setReason(stepsovcCase.getHiv_counseling_na_reason());
        referral.getNewDiagnosis().setReason(stepsovcCase.getNew_diagnosis_na_reason());
        referral.getOtherHealthServices().setReason(stepsovcCase.getOther_health_service_na_reason());
        referral.getPainManagement().setReason((stepsovcCase.getPain_management_na_reason()));
        referral.getPmtct().setReason(stepsovcCase.getPmtct_na_reason());
        referral.getSexuallyTransmittedInfection().setReason(stepsovcCase.getSexually_transmitted_na_reason());

        return referral;
    }
}
