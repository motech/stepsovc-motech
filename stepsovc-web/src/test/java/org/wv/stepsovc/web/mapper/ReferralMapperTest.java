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
        BeneficiaryCase beneficiaryCase = new BeneficiaryCase();
        beneficiaryCase.setArt_adherence_counseling("ARTAdherance-Referred");
        beneficiaryCase.setBeneficiary_code("Ben_Code");
        beneficiaryCase.setBeneficiary_dob("10-10-1967");
        beneficiaryCase.setBeneficiary_name("Ben_Name");
        beneficiaryCase.setCaregiver_id("CareGiverID");
        beneficiaryCase.setCaregiver_name("CareGiverName");
        beneficiaryCase.setCondoms("Condoms-Referred");
        beneficiaryCase.setEnd_of_life_hospice("End Of Life Hospice-Not Referred");
        beneficiaryCase.setFamily_planning("Family Planning-Referred");
        beneficiaryCase.setFollowup_date("2012-12-12");
        beneficiaryCase.setFollowup_required("Follow-up Required-Yes");
        beneficiaryCase.setHiv_counseling("HIV Counselling-Referred");
        beneficiaryCase.setPmtct("PMTCT-Referred");
        beneficiaryCase.setNew_diagnosis("New Diagnosis-Referred");
        beneficiaryCase.setSexually_transmitted_infection("SexuallyTransmittedInfection-Not Referred");
        beneficiaryCase.setPain_management("Pain Management-Referred");
        beneficiaryCase.setOther_health_services("Other Health Services-Referred");
        beneficiaryCase.setVisit_date("2012-4-12");
        beneficiaryCase.setService_date("1988-12-23");
        Referral newReferral = null;

        newReferral = new ReferralMapper().map(beneficiaryCase);
        assertThat(newReferral.getArtAdherenceCounseling(),is(beneficiaryCase.getArt_adherence_counseling()));
        assertThat(newReferral.getBeneficiaryCode(),is(beneficiaryCase.getBeneficiary_code()));
        assertThat(newReferral.getCondoms(),is(beneficiaryCase.getCondoms()));
        assertThat(newReferral.getEndOfLifeHospice(),is(beneficiaryCase.getEnd_of_life_hospice()));
        assertThat(newReferral.getFacilityId(),is(beneficiaryCase.getService_provider()));
        assertThat(newReferral.getFamilyPlanning(),is(beneficiaryCase.getFamily_planning()));
        assertThat(newReferral.getHivCounseling(),is(beneficiaryCase.getHiv_counseling()));
        assertThat(newReferral.getNewDiagnosis(),is(beneficiaryCase.getNew_diagnosis()));
        assertThat(newReferral.getOtherHealthServices(),is(beneficiaryCase.getOther_health_services()));
        assertThat(newReferral.getPainManagement(),is(beneficiaryCase.getPain_management()));
        assertThat(newReferral.getPmtct(),is(beneficiaryCase.getPmtct()));
        assertThat(newReferral.getEndOfLifeHospice(),is(beneficiaryCase.getEnd_of_life_hospice()));
        assertThat(newReferral.getServiceDate(),is(ReferralMapper.dateFormat.parse(beneficiaryCase.getService_date())));
        assertThat(newReferral.getSexuallyTransmittedInfection(),is(beneficiaryCase.getSexually_transmitted_infection()));
        assertThat(newReferral.getVisitDate(),is(ReferralMapper.dateFormat.parse(beneficiaryCase.getVisit_date())));
        assertThat(newReferral.getFollowupRequired(), is(beneficiaryCase.getFollowup_required()));
        assertThat(newReferral.getFollowupDate(),is(ReferralMapper.dateFormat.parse(beneficiaryCase.getFollowup_date())));
    }

    @Test(expected = ParseException.class)
    public void shouldFailWhenTheDateFormatIsWrong() throws ParseException {

        BeneficiaryCase beneficiaryCase = new BeneficiaryCase();
        beneficiaryCase.setArt_adherence_counseling("ARTAdherance-Referred");
        beneficiaryCase.setFamily_planning("Family Planning-Referred");
        beneficiaryCase.setFollowup_date("20/12/1212");
        beneficiaryCase.setOther_health_services("Other Health Services-Referred");

        beneficiaryCase.setService_date("12/12/1233");
        Referral newReferral = null;

        newReferral = new ReferralMapper().map(beneficiaryCase);

    }



}
