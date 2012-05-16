package org.wv.stepsovc.web.mapper;

import org.apache.commons.lang.StringUtils;
import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.domain.Service;
import org.wv.stepsovc.web.request.ServiceUnavailedType;
import org.wv.stepsovc.web.request.StepsovcCase;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class ReferralMapper {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static String REFERRED = "Referred";
    static String NOT_REFERRED = "Not Referred";
    static String SERVICE_RECEIVED = "Received";
    static String SERVICE_NOT_AVAILED = "Not Availed";
    public static final String RRID_PREFIX = "OVC-REF-";

    public Referral map(StepsovcCase stepsovcCase) {
        Referral newReferral = new Referral();
        newReferral.setBeneficiaryCode(stepsovcCase.getBeneficiary_code());
        newReferral.setFacilityCode(stepsovcCase.getFacility_code());
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
        newReferral.setActive(true);
        newReferral.setOvcId(RRID_PREFIX + UUID.randomUUID().toString());
        newReferral.setServiceDetails(stepsovcCase.getService_details());

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
        referral.setServiceDetails(stepsovcCase.getService_details());

        referral.setFacilityCode(stepsovcCase.getFacility_code());
        referral.setServiceDate(stepsovcCase.getService_date());

        return referral;
    }

    public Referral updateReferral(Referral referral, StepsovcCase stepsovcCase) {
        referral.getCondoms().setReason(serviceUnavailedReason(stepsovcCase.getCondoms_na_reason()));
        referral.getArtAdherenceCounseling().setReason(serviceUnavailedReason(stepsovcCase.getArt_adherence_na_reason()));
        referral.getEndOfLifeHospice().setReason(serviceUnavailedReason(stepsovcCase.getEnd_of_life_hospice_na_reason()));
        referral.getFamilyPlanning().setReason(serviceUnavailedReason(stepsovcCase.getFamily_planning_na_reason()));
        referral.getHivCounseling().setReason(serviceUnavailedReason(stepsovcCase.getHiv_counseling_na_reason()));
        referral.getNewDiagnosis().setReason(serviceUnavailedReason(stepsovcCase.getNew_diagnosis_na_reason()));
        referral.getOtherHealthServices().setReason(serviceUnavailedReason(stepsovcCase.getOther_health_service_na_reason()));
        referral.getPainManagement().setReason((serviceUnavailedReason(stepsovcCase.getPain_management_na_reason())));
        referral.getPmtct().setReason(serviceUnavailedReason(stepsovcCase.getPmtct_na_reason()));
        referral.getSexuallyTransmittedInfection().setReason(serviceUnavailedReason(stepsovcCase.getSexually_transmitted_na_reason()));

        return referral;
    }

    private String serviceUnavailedReason(String key) {
        return StringUtils.isNotEmpty(key) ? ServiceUnavailedType.valueOf(key).getValue() : "" ;
    }
}
