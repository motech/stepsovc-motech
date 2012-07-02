package org.wv.stepsovc.tools.mapper;

import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.Service;
import org.wv.stepsovc.tools.domain.ReferralData;

import java.util.Map;

import static org.wv.stepsovc.core.domain.ServiceType.*;

@Component
public class ReferralDataMapper {

    public ReferralData map(Referral referral) {
        ReferralData referralData = new ReferralData();
        referralData.setCaregiverId(referral.getCgId());
        referralData.setFacilityCode(referral.getFacilityCode());
        referralData.setReferralId(referral.getOvcId());
        referralData.setFollowupRequired(referral.getFollowupRequired());
        referralData.setServiceDetails(referral.getServiceDetails());
        referralData.setServiceDate(referral.getServiceDate());

        boolean isAnyServiceReceived = referral.anyServiceReceived();
        referralData.setSigned(byteValue(isAnyServiceReceived));
        if (isAnyServiceReceived)
            referralData.setSignedDate(referral.getVisitDate());

        Map<String, Service> referredServices = referral.getReferredServices();
        referralData.setArtReceived(byteValue(referredServices.get(ART_ADHERENCE.getCode()).isProvided()));
        referralData.setArtReferred(byteValue(referredServices.get(ART_ADHERENCE.getCode()).isReferred()));
        referralData.setCondomsReceived(byteValue(referredServices.get(CONDOMS.getCode()).isProvided()));
        referralData.setCondomsReferred(byteValue(referredServices.get(CONDOMS.getCode()).isReferred()));
        referralData.setCounsellingTestingReceived(byteValue(referredServices.get(HIV_COUNSELING.getCode()).isProvided()));
        referralData.setCounsellingTestingReferred(byteValue(referredServices.get(HIV_COUNSELING.getCode()).isReferred()));
        referralData.setDiagnosisReceived(byteValue(referredServices.get(NEW_DIAGNOSIS.getCode()).isProvided()));
        referralData.setDiagnosisReferred(byteValue(referredServices.get(NEW_DIAGNOSIS.getCode()).isReferred()));
        referralData.setFamilyPlanningReceived(byteValue(referredServices.get(FAMILY_PLANNING.getCode()).isProvided()));
        referralData.setFamilyPlanningReferred(byteValue(referredServices.get(FAMILY_PLANNING.getCode()).isReferred()));
        referralData.setHospitalAdmissionReceived(byteValue(referredServices.get(END_OF_LIFE_HOSPICE.getCode()).isProvided()));
        referralData.setHospitalAdmissionReferred(byteValue(referredServices.get(END_OF_LIFE_HOSPICE.getCode()).isReferred()));
        referralData.setOtherServiceReceived(byteValue(referredServices.get(OTHER_HEALTH_SERVICES.getCode()).isProvided()));
        referralData.setOtherServiceReferred(byteValue(referredServices.get(OTHER_HEALTH_SERVICES.getCode()).isReferred()));
        referralData.setPainManagementReceived(byteValue(referredServices.get(PAIN_MANAGEMENT.getCode()).isProvided()));
        referralData.setPainManagementReferred(byteValue(referredServices.get(PAIN_MANAGEMENT.getCode()).isReferred()));
        referralData.setPmtctReceived(byteValue(referredServices.get(PMTCT.getCode()).isProvided()));
        referralData.setPmtctReferred(byteValue(referredServices.get(PMTCT.getCode()).isReferred()));
        referralData.setSexTransInfectionReceived(byteValue(referredServices.get(SEXUALLY_TRANSMITTED_INFEC.getCode()).isProvided()));
        referralData.setSexTransInfectionReferred(byteValue(referredServices.get(SEXUALLY_TRANSMITTED_INFEC.getCode()).isReferred()));

        return referralData;
    }

    private byte byteValue(boolean value) {
        return (byte) (value ? 1 : 0);
    }
}
