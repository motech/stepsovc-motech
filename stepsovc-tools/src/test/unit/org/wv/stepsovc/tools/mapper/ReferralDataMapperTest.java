package org.wv.stepsovc.tools.mapper;

import fixture.ReferralFixture;
import org.junit.Test;
import org.wv.stepsovc.core.domain.Referral;
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
        assertEquals(referral.getServiceDetails(), referralData.getServiceDetails());
        assertEquals(referral.getServiceDate(), referralData.getServiceDate());

        assertEquals(0, referralData.getFollowupRequired());
        assertEquals(1, referralData.getArtReferred());
        assertEquals(1, referralData.getArtReceived());
        assertEquals(1, referralData.getCondomsReferred());
        assertEquals(1, referralData.getCondomsReceived());

        assertEquals(1, referralData.getHospitalAdmissionReferred());
        assertEquals(0, referralData.getHospitalAdmissionReceived());
        assertEquals(1, referralData.getFamilyPlanningReferred());
        assertEquals(0, referralData.getFamilyPlanningReceived());

        assertEquals(0, referralData.getCounsellingTestingReferred());
        assertEquals(1, referralData.getCounsellingTestingReceived());
        assertEquals(0, referralData.getDiagnosisReferred());
        assertEquals(1, referralData.getDiagnosisReceived());

        assertEquals(0, referralData.getOtherServiceReferred());
        assertEquals(0, referralData.getOtherServiceReceived());
        assertEquals(0, referralData.getPainManagementReferred());
        assertEquals(0, referralData.getPainManagementReceived());

        assertEquals(1, referralData.getPmtctReferred());
        assertEquals(1, referralData.getPmtctReceived());
        assertEquals(1, referralData.getSexTransInfectionReferred());
        assertEquals(1, referralData.getSexTransInfectionReceived());
    }

}
