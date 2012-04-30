package org.wv.stepsovc.web.mapper;

import org.junit.Test;
import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.request.BeneficiaryCase;

import java.text.ParseException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ReferralMapperTest {

    @Test
    public void shouldMapBeneficiaryCaseToReferral() throws ParseException {
        BeneficiaryCase beneficiaryCase = createCaseForReferral("Ben_Code", "2012-4-12");
        Referral newReferral = null;

        newReferral = new ReferralMapper().map(beneficiaryCase);
        assertThat(newReferral.getArtAdherenceCounseling().isReferred(), is(ReferralMapper.REFERRED.equals(beneficiaryCase.getArt_adherence_counseling())));
        assertThat(newReferral.getBeneficiaryCode(),is(beneficiaryCase.getBeneficiary_code()));
        assertThat(newReferral.getCondoms().isReferred(),is(ReferralMapper.REFERRED.equals(beneficiaryCase.getCondoms())));
        assertThat(newReferral.getEndOfLifeHospice().isReferred(),is(ReferralMapper.REFERRED.equals(beneficiaryCase.getEnd_of_life_hospice())));
        assertThat(newReferral.getFacilityId(),is(beneficiaryCase.getService_provider()));
        assertThat(newReferral.getFamilyPlanning().isReferred(),is(ReferralMapper.REFERRED.equals(beneficiaryCase.getFamily_planning())));
        assertThat(newReferral.getHivCounseling().isReferred(),is(ReferralMapper.REFERRED.equals(beneficiaryCase.getHiv_counseling())));
        assertThat(newReferral.getNewDiagnosis().isReferred(),is(ReferralMapper.REFERRED.equals(beneficiaryCase.getNew_diagnosis())));
        assertThat(newReferral.getOtherHealthServices().isReferred(),is(ReferralMapper.REFERRED.equals(beneficiaryCase.getOther_health_services())));
        assertThat(newReferral.getPainManagement().isReferred(),is(ReferralMapper.REFERRED.equals(beneficiaryCase.getPain_management())));
        assertThat(newReferral.getPmtct().isReferred(),is(ReferralMapper.REFERRED.equals(beneficiaryCase.getPmtct())));
        assertThat(newReferral.getServiceDate(),is(beneficiaryCase.getService_date()));
        assertThat(newReferral.getSexuallyTransmittedInfection().isReferred(),is(ReferralMapper.REFERRED.equals(beneficiaryCase.getSexually_transmitted_infection())));
        assertThat(newReferral.getVisitDate(),is(beneficiaryCase.getVisit_date()));
        assertThat(newReferral.getFollowupRequired(), is(beneficiaryCase.getFollowup_required()));
        assertThat(newReferral.getFollowupDate(),is(beneficiaryCase.getFollowup_date()));
    }

    public static BeneficiaryCase createCaseForReferral(String code, String serviceDate) {
        BeneficiaryCase beneficiaryCase = createNewCase(code);
        beneficiaryCase.setCondoms("Referred");
        beneficiaryCase.setArt_adherence_counseling("Referred");
        beneficiaryCase.setEnd_of_life_hospice("Not Referred");
        beneficiaryCase.setFamily_planning("Referred");
        beneficiaryCase.setFollowup_date("2012-12-12");
        beneficiaryCase.setFollowup_required("Yes");
        beneficiaryCase.setHiv_counseling("Referred");
        beneficiaryCase.setPmtct("Referred");
        beneficiaryCase.setNew_diagnosis("Referred");
        beneficiaryCase.setSexually_transmitted_infection("Not Referred");
        beneficiaryCase.setPain_management("Referred");
        beneficiaryCase.setOther_health_services("Referred");
        beneficiaryCase.setVisit_date("1988-12-23");
        beneficiaryCase.setService_date(serviceDate);
        return beneficiaryCase;
    }

    public static BeneficiaryCase createNewCase(String code) {
        BeneficiaryCase beneficiaryCase = new BeneficiaryCase();
        beneficiaryCase.setBeneficiary_code(code);
        beneficiaryCase.setBeneficiary_dob("10-10-1967");
        beneficiaryCase.setBeneficiary_name("Ben_Name");
        beneficiaryCase.setCaregiver_id("CareGiverID");
        beneficiaryCase.setCaregiver_name("CareGiverName");
        return beneficiaryCase;
    }

    @Test
    public void shouldUpdateService() throws ParseException {
        BeneficiaryCase beneficiaryCase = createCaseForUpdateService("Ben_Code", "2012-4-12");

        Referral newReferral = new ReferralMapper().map(beneficiaryCase);

        newReferral=new ReferralMapper().updateServices(newReferral,beneficiaryCase);
        assertThat(newReferral.getArtAdherenceCounseling().isProvided(),is(ReferralMapper.SERVICE_RECEIVED.equals(beneficiaryCase.getArt_adherence_counseling())));
        assertThat(newReferral.getCondoms().isProvided(),is(ReferralMapper.SERVICE_RECEIVED.equals(beneficiaryCase.getCondoms())));
        assertThat(newReferral.getEndOfLifeHospice().isProvided(),is(ReferralMapper.SERVICE_RECEIVED.equals(beneficiaryCase.getEnd_of_life_hospice())));
        assertThat(newReferral.getFamilyPlanning().isProvided(),is(ReferralMapper.SERVICE_RECEIVED.equals(beneficiaryCase.getFamily_planning())));
        assertThat(newReferral.getHivCounseling().isProvided(),is(ReferralMapper.SERVICE_RECEIVED.equals(beneficiaryCase.getHiv_counseling())));
        assertThat(newReferral.getNewDiagnosis().isProvided(),is(ReferralMapper.SERVICE_RECEIVED.equals(beneficiaryCase.getNew_diagnosis())));
        assertThat(newReferral.getOtherHealthServices().isProvided(),is(ReferralMapper.SERVICE_RECEIVED.equals(beneficiaryCase.getOther_health_services())));
        assertThat(newReferral.getPainManagement().isProvided(),is(ReferralMapper.SERVICE_RECEIVED.equals(beneficiaryCase.getPain_management())));
        assertThat(newReferral.getPmtct().isProvided(),is(ReferralMapper.SERVICE_RECEIVED.equals(beneficiaryCase.getPmtct())));
        assertThat(newReferral.getEndOfLifeHospice().isProvided(),is(ReferralMapper.SERVICE_RECEIVED.equals(beneficiaryCase.getEnd_of_life_hospice())));
        assertThat(newReferral.getSexuallyTransmittedInfection().isProvided(),is(ReferralMapper.SERVICE_RECEIVED.equals(beneficiaryCase.getSexually_transmitted_infection())));
        assertThat(newReferral.getFacilityId(),is(beneficiaryCase.getService_provider()));
        assertThat(newReferral.getServiceDate(),is(beneficiaryCase.getService_date()));

    }

    public static BeneficiaryCase createCaseForUpdateService(String code, String visitDate) {
        BeneficiaryCase beneficiaryCase = createCaseForReferral(code, null);
        beneficiaryCase.setCondoms("Received");
        beneficiaryCase.setEnd_of_life_hospice("Not Availed");
        beneficiaryCase.setFamily_planning("Received");
        beneficiaryCase.setFollowup_date("2012-12-12");
        beneficiaryCase.setFollowup_required("Yes");
        beneficiaryCase.setHiv_counseling("Received");
        beneficiaryCase.setPmtct("Received");
        beneficiaryCase.setNew_diagnosis("Received");
        beneficiaryCase.setSexually_transmitted_infection("Not Availed");
        beneficiaryCase.setPain_management("Received");
        beneficiaryCase.setOther_health_services("Received");
        beneficiaryCase.setArt_adherence_counseling("Received");
        beneficiaryCase.setService_provider("ABC");
        beneficiaryCase.setService_date(null);
        beneficiaryCase.setVisit_date(visitDate);

        return beneficiaryCase;
    }


}
