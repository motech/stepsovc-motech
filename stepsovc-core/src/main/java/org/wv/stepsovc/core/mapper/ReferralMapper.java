package org.wv.stepsovc.core.mapper;

import org.apache.commons.lang.StringUtils;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.Service;
import org.wv.stepsovc.core.request.ServiceUnavailedType;
import org.wv.stepsovc.core.request.StepsovcCase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.wv.stepsovc.core.domain.ServiceType.*;

public class ReferralMapper {

    public static final String REFERRED = "Referred";
    public static final String NOT_REFERRED = "Not Referred";
    public static final String SERVICE_RECEIVED = "Received";
    public static final String SERVICE_NOT_AVAILED = "Not Availed";
    public static final String REF_ID_PREFIX = "OVC-REF-";

    public Referral map(StepsovcCase stepsovcCase) {
        Referral newReferral = new Referral();
        newReferral.setBeneficiaryCode(stepsovcCase.getBeneficiary_code());
        newReferral.setFacilityCode(stepsovcCase.getFacility_code());
        newReferral.setCgId(stepsovcCase.getUser_id());
        newReferral.setServiceDate(stepsovcCase.getService_date());
        newReferral.setFollowupDate(stepsovcCase.getFollowup_date());
        newReferral.setVisitDate(stepsovcCase.getVisit_date());
        newReferral.setFollowupRequired(stepsovcCase.getFollowup_required());

        Map<String, Service> referredServices = new HashMap<String, Service>();
        referredServices.put(ART_ADHERENCE.getCode(), new Service(REFERRED.equals(stepsovcCase.getArt_adherence_counseling()), ART_ADHERENCE));
        referredServices.put(CONDOMS.getCode(), new Service(REFERRED.equals(stepsovcCase.getCondoms()), CONDOMS));
        referredServices.put(END_OF_LIFE_HOSPICE.getCode(), new Service(REFERRED.equals(stepsovcCase.getEnd_of_life_hospice()), END_OF_LIFE_HOSPICE));
        referredServices.put(FAMILY_PLANNING.getCode(), new Service(REFERRED.equals(stepsovcCase.getFamily_planning()), FAMILY_PLANNING));
        referredServices.put(HIVE_COUNSELING.getCode(), new Service(REFERRED.equals(stepsovcCase.getHiv_counseling()), HIVE_COUNSELING));
        referredServices.put(OTHER_HEALTH_SERVICES.getCode(), new Service(REFERRED.equals(stepsovcCase.getOther_health_services()), OTHER_HEALTH_SERVICES));
        referredServices.put(NEW_DIAGNOSIS.getCode(), new Service(REFERRED.equals(stepsovcCase.getNew_diagnosis()), NEW_DIAGNOSIS));
        referredServices.put(PAIN_MANAGEMENT.getCode(), new Service(REFERRED.equals(stepsovcCase.getPain_management()), PAIN_MANAGEMENT));
        referredServices.put(PMTCT.getCode(), new Service(REFERRED.equals(stepsovcCase.getPmtct()), PMTCT));
        referredServices.put(SEXUALLY_TRANSMITTED_INFEC.getCode(), new Service(REFERRED.equals(stepsovcCase.getSexually_transmitted_infection()), SEXUALLY_TRANSMITTED_INFEC));

        newReferral.setReferredServices(referredServices);
        newReferral.setActive(true);
        newReferral.setOvcId(REF_ID_PREFIX + UUID.randomUUID().toString());
        newReferral.setServiceDetails(stepsovcCase.getService_details());

        return newReferral;
    }

    public Referral updateServices(Referral referral, StepsovcCase stepsovcCase) {
        Map<String, Service> referredServices = referral.getReferredServices();
        referredServices.get(CONDOMS.getCode()).setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getCondoms()));
        referredServices.get(ART_ADHERENCE.getCode()).setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getArt_adherence_counseling()));
        referredServices.get(END_OF_LIFE_HOSPICE.getCode()).setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getEnd_of_life_hospice()));
        referredServices.get(FAMILY_PLANNING.getCode()).setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getFamily_planning()));
        referredServices.get(HIVE_COUNSELING.getCode()).setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getHiv_counseling()));
        referredServices.get(NEW_DIAGNOSIS.getCode()).setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getNew_diagnosis()));
        referredServices.get(OTHER_HEALTH_SERVICES.getCode()).setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getOther_health_services()));
        referredServices.get(PAIN_MANAGEMENT.getCode()).setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getPain_management()));
        referredServices.get(PMTCT.getCode()).setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getPmtct()));
        referredServices.get(SEXUALLY_TRANSMITTED_INFEC.getCode()).setProvided(SERVICE_RECEIVED.equals(stepsovcCase.getSexually_transmitted_infection()));

        referral.setVisitDate(stepsovcCase.getVisit_date());
        referral.setServiceDetails(stepsovcCase.getService_details());

        referral.setFacilityCode(stepsovcCase.getFacility_code());
        referral.setServiceDate(stepsovcCase.getService_date());

        return referral;
    }

    public Referral updateReferral(Referral referral, StepsovcCase stepsovcCase) {
        Map<String, Service> referredServices = referral.getReferredServices();
        referredServices.get(CONDOMS.getCode()).setReason(serviceUnavailedReason(stepsovcCase.getCondoms_na_reason()));
        referredServices.get(ART_ADHERENCE.getCode()).setReason(serviceUnavailedReason(stepsovcCase.getArt_adherence_na_reason()));
        referredServices.get(END_OF_LIFE_HOSPICE.getCode()).setReason(serviceUnavailedReason(stepsovcCase.getEnd_of_life_hospice_na_reason()));
        referredServices.get(FAMILY_PLANNING.getCode()).setReason(serviceUnavailedReason(stepsovcCase.getFamily_planning_na_reason()));
        referredServices.get(HIVE_COUNSELING.getCode()).setReason(serviceUnavailedReason(stepsovcCase.getHiv_counseling_na_reason()));
        referredServices.get(NEW_DIAGNOSIS.getCode()).setReason(serviceUnavailedReason(stepsovcCase.getNew_diagnosis_na_reason()));
        referredServices.get(OTHER_HEALTH_SERVICES.getCode()).setReason(serviceUnavailedReason(stepsovcCase.getOther_health_service_na_reason()));
        referredServices.get(PAIN_MANAGEMENT.getCode()).setReason((serviceUnavailedReason(stepsovcCase.getPain_management_na_reason())));
        referredServices.get(PMTCT.getCode()).setReason(serviceUnavailedReason(stepsovcCase.getPmtct_na_reason()));
        referredServices.get(SEXUALLY_TRANSMITTED_INFEC.getCode()).setReason(serviceUnavailedReason(stepsovcCase.getSexually_transmitted_na_reason()));

        return referral;
    }

    private String serviceUnavailedReason(String key) {
        return StringUtils.isNotEmpty(key) ? ServiceUnavailedType.valueOf(key).getValue() : "";
    }
}
