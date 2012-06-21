package org.wv.stepsovc.tools.mapper;

import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.Service;
import org.wv.stepsovc.core.domain.ServiceType;
import org.wv.stepsovc.tools.domain.ReferralData;

import java.util.Map;

@Component
public class ReferralDataMapper {

    public static final String FOLLOW_UP_REQUIRED = "yes";

    public ReferralData map(Referral referral) {
        ReferralData referralData = new ReferralData();
        referralData.setCaregiverId(referral.getCgId());
        referralData.setFacilityCode(referral.getFacilityCode());
        referralData.setReferralId(referral.getOvcId());
        referralData.setFollowupRequired(FOLLOW_UP_REQUIRED.equals(referral.getFollowupRequired()));
        referralData.setServiceDetails(referral.getServiceDetails());
        referralData.setServiceDate(referral.getServiceDate());

        if (referral.anyServiceReceived()) {
            referralData.setSigned(true);
            referralData.setSignedDate(referral.getVisitDate());
        }

        Map<String, Service> referredServices = referral.getReferredServices();
        referralData.setArtReceived(referredServices.get(ServiceType.ART_ADHERENCE.getCode()).isProvided());
        referralData.setArtReferred(referredServices.get(ServiceType.ART_ADHERENCE.getCode()).isReferred());
        referralData.setCondomsReceived(referredServices.get(ServiceType.CONDOMS.getCode()).isProvided());
        referralData.setCondomsReferred(referredServices.get(ServiceType.CONDOMS.getCode()).isReferred());
        referralData.setCounsellingTestingReceived(referredServices.get(ServiceType.HIV_COUNSELING.getCode()).isProvided());
        referralData.setCounsellingTestingReferred(referredServices.get(ServiceType.HIV_COUNSELING.getCode()).isReferred());
        referralData.setDiagnosisReceived(referredServices.get(ServiceType.NEW_DIAGNOSIS.getCode()).isProvided());
        referralData.setDiagnosisReferred(referredServices.get(ServiceType.NEW_DIAGNOSIS.getCode()).isReferred());
        referralData.setFamilyPlanningReceived(referredServices.get(ServiceType.FAMILY_PLANNING.getCode()).isProvided());
        referralData.setFamilyPlanningReferred(referredServices.get(ServiceType.FAMILY_PLANNING.getCode()).isReferred());
        referralData.setHospitalAdmissionReceived(referredServices.get(ServiceType.END_OF_LIFE_HOSPICE.getCode()).isProvided());
        referralData.setHospitalAdmissionReferred(referredServices.get(ServiceType.END_OF_LIFE_HOSPICE.getCode()).isReferred());
        referralData.setOtherServiceReceived(referredServices.get(ServiceType.OTHER_HEALTH_SERVICES.getCode()).isProvided());
        referralData.setOtherServiceReferred(referredServices.get(ServiceType.OTHER_HEALTH_SERVICES.getCode()).isReferred());
        referralData.setPainManagementReceived(referredServices.get(ServiceType.PAIN_MANAGEMENT.getCode()).isProvided());
        referralData.setPainManagementReferred(referredServices.get(ServiceType.PAIN_MANAGEMENT.getCode()).isReferred());
        referralData.setPmtctReceived(referredServices.get(ServiceType.PMTCT.getCode()).isProvided());
        referralData.setPmtctReferred(referredServices.get(ServiceType.PMTCT.getCode()).isReferred());
        referralData.setSexTransInfectionReceived(referredServices.get(ServiceType.SEXUALLY_TRANSMITTED_INFEC.getCode()).isProvided());
        referralData.setSexTransInfectionReferred(referredServices.get(ServiceType.SEXUALLY_TRANSMITTED_INFEC.getCode()).isReferred());

        return referralData;
    }
}
