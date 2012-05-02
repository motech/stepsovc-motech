package org.wv.stepsovc.web.mapper;

import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.domain.Service;
import org.wv.stepsovc.web.request.BeneficiaryCase;

import java.text.SimpleDateFormat;

public class ReferralMapper {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static String REFERRED = "Referred";
    static String NOT_REFERRED = "Not Referred";
    static String SERVICE_RECEIVED = "Received";
    static String SERVICE_NOT_AVAILED = "Not Availed";

    public Referral map(BeneficiaryCase beneficiaryCase) {
        Referral newReferral = new Referral();
        newReferral.setBeneficiaryCode(beneficiaryCase.getBeneficiary_code());
        newReferral.setFacilityId(beneficiaryCase.getService_provider());
        newReferral.setServiceDate(beneficiaryCase.getService_date());
        newReferral.setFollowupDate(beneficiaryCase.getFollowup_date());
        newReferral.setVisitDate(beneficiaryCase.getVisit_date());
        newReferral.setFollowupRequired(beneficiaryCase.getFollowup_required());
        newReferral.setArtAdherenceCounseling(new Service(REFERRED.equals(beneficiaryCase.getArt_adherence_counseling())));
        newReferral.setCondoms(new Service(REFERRED.equals(beneficiaryCase.getCondoms())));
        newReferral.setEndOfLifeHospice(new Service(REFERRED.equals(beneficiaryCase.getEnd_of_life_hospice())));
        newReferral.setFamilyPlanning(new Service(REFERRED.equals(beneficiaryCase.getFamily_planning())));
        newReferral.setHivCounseling(new Service(REFERRED.equals(beneficiaryCase.getHiv_counseling())));
        newReferral.setOtherHealthServices(new Service(REFERRED.equals(beneficiaryCase.getOther_health_services())));
        newReferral.setNewDiagnosis(new Service(REFERRED.equals(beneficiaryCase.getNew_diagnosis())));
        newReferral.setPainManagement(new Service(REFERRED.equals(beneficiaryCase.getPain_management())));
        newReferral.setPmtct(new Service(REFERRED.equals(beneficiaryCase.getPmtct())));
        newReferral.setSexuallyTransmittedInfection(new Service(REFERRED.equals(beneficiaryCase.getSexually_transmitted_infection())));

        return newReferral;
    }

    public Referral updateServices(Referral referral, BeneficiaryCase beneficiaryCase) {
        referral.getCondoms().setProvided(SERVICE_RECEIVED.equals(beneficiaryCase.getCondoms()));
        referral.getArtAdherenceCounseling().setProvided(SERVICE_RECEIVED.equals(beneficiaryCase.getArt_adherence_counseling()));
        referral.getEndOfLifeHospice().setProvided(SERVICE_RECEIVED.equals(beneficiaryCase.getEnd_of_life_hospice()));
        referral.getFamilyPlanning().setProvided(SERVICE_RECEIVED.equals(beneficiaryCase.getFamily_planning()));
        referral.getHivCounseling().setProvided(SERVICE_RECEIVED.equals(beneficiaryCase.getHiv_counseling()));
        referral.getNewDiagnosis().setProvided(SERVICE_RECEIVED.equals(beneficiaryCase.getNew_diagnosis()));
        referral.getOtherHealthServices().setProvided(SERVICE_RECEIVED.equals(beneficiaryCase.getOther_health_services()));
        referral.getPainManagement().setProvided(SERVICE_RECEIVED.equals(beneficiaryCase.getPain_management()));
        referral.getPmtct().setProvided(SERVICE_RECEIVED.equals(beneficiaryCase.getPmtct()));
        referral.getSexuallyTransmittedInfection().setProvided(SERVICE_RECEIVED.equals(beneficiaryCase.getSexually_transmitted_infection()));
        referral.setVisitDate(beneficiaryCase.getVisit_date());

        //TODO: check if we need to create new referral if facility id is set
        referral.setFacilityId(beneficiaryCase.getService_provider());
        referral.setServiceDate(beneficiaryCase.getService_date());

        return referral;
    }

    public Referral updateReferral(Referral referral, BeneficiaryCase beneficiaryCase) {
        referral.getCondoms().setReason(beneficiaryCase.getCondoms_na_reason());
        referral.getArtAdherenceCounseling().setReason(beneficiaryCase.getArt_adherence_na_reason());
        referral.getEndOfLifeHospice().setReason(beneficiaryCase.getEnd_of_life_hospice_na_reason());
        referral.getFamilyPlanning().setReason(beneficiaryCase.getFamily_planning_na_reason());
        referral.getHivCounseling().setReason(beneficiaryCase.getHiv_counseling_na_reason());
        referral.getNewDiagnosis().setReason(beneficiaryCase.getNew_diagnosis_na_reason());
        referral.getOtherHealthServices().setReason(beneficiaryCase.getOther_health_service_na_reason());
        referral.getPainManagement().setReason((beneficiaryCase.getPain_management_na_reason()));
        referral.getPmtct().setReason(beneficiaryCase.getPmtct_na_reason());
        referral.getSexuallyTransmittedInfection().setReason(beneficiaryCase.getSexually_transmitted_na_reason());

        return referral;
    }
}
