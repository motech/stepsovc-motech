package org.wv.stepsovc.tools.mapper;

import fixture.ReferralFixture;
import org.junit.Test;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.Service;
import org.wv.stepsovc.core.domain.ServiceType;
import org.wv.stepsovc.tools.domain.ReferralData;

import static junit.framework.Assert.assertEquals;

public class ReferralDataMapperTest {

    @Test
    public void shouldMapReferralDataFromReferral() {
        ReferralDataMapper referralDataMapper = new ReferralDataMapper();
        Referral referral = ReferralFixture.getReferral();

        ReferralData referralData = referralDataMapper.map(referral);
        assertEquals(referral.getOvcId(), referralData.getReferralId());
        assertEquals(referral.getCgId(), referralData.getCaregiverId());
        assertEquals(referral.getFacilityCode(), referralData.getFacilityCode());
        assertEquals(ReferralDataMapper.FOLLOW_UP_REQUIRED.equals(referral.getFollowupRequired()), referralData.getFollowupRequired());
        assertEquals(referral.getServiceDetails(), referralData.getServiceDetails());
        assertEquals(referral.getServiceDate(), referralData.getServiceDate());

        assertValues(referral.getReferredServices().get(ServiceType.ART_ADHERENCE.getCode()), referralData.getArtReferred(), referralData.getArtReceived());
        assertValues(referral.getReferredServices().get(ServiceType.CONDOMS.getCode()), referralData.getCondomsReferred(), referralData.getCondomsReceived());
        assertValues(referral.getReferredServices().get(ServiceType.END_OF_LIFE_HOSPICE.getCode()), referralData.getHospitalAdmissionReferred(), referralData.getHospitalAdmissionReceived());
        assertValues(referral.getReferredServices().get(ServiceType.FAMILY_PLANNING.getCode()), referralData.getFamilyPlanningReferred(), referralData.getFamilyPlanningReceived());
        assertValues(referral.getReferredServices().get(ServiceType.HIV_COUNSELING.getCode()), referralData.getCounsellingTestingReferred(), referralData.getCounsellingTestingReceived());
        assertValues(referral.getReferredServices().get(ServiceType.NEW_DIAGNOSIS.getCode()), referralData.getDiagnosisReferred(), referralData.getDiagnosisReceived());
        assertValues(referral.getReferredServices().get(ServiceType.OTHER_HEALTH_SERVICES.getCode()), referralData.getOtherServiceReferred(), referralData.getOtherServiceReceived());
        assertValues(referral.getReferredServices().get(ServiceType.PAIN_MANAGEMENT.getCode()), referralData.getPainManagementReferred(), referralData.getPainManagementReceived());
        assertValues(referral.getReferredServices().get(ServiceType.PMTCT.getCode()), referralData.getPmtctReferred(), referralData.getPmtctReceived());
        assertValues(referral.getReferredServices().get(ServiceType.SEXUALLY_TRANSMITTED_INFEC.getCode()), referralData.getSexTransInfectionReferred(), referralData.getSexTransInfectionReceived());
    }

    private void assertValues(Service service, boolean referred, boolean received) {
        assertEquals(service.isReferred(), referred);
        assertEquals(service.isProvided(), received);
    }

}
