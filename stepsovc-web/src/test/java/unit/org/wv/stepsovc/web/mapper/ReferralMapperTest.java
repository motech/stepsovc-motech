package org.wv.stepsovc.web.mapper;

import org.junit.Test;
import org.wv.stepsovc.commcare.domain.CaseType;
import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.request.StepsovcCase;

import java.text.ParseException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ReferralMapperTest {

    @Test
    public void shouldMapBeneficiaryCaseToReferral() throws ParseException {
        StepsovcCase stepsovcCase = createCaseForReferral("Ben_Code", "2012-4-12", "FAC001");
        Referral newReferral = null;

        newReferral = new ReferralMapper().map(stepsovcCase);
        assertReferrals(stepsovcCase, newReferral);
    }

    public static void assertReferrals(StepsovcCase stepsovcCase, Referral newReferral) {
        assertThat(newReferral.getArtAdherenceCounseling().isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getArt_adherence_counseling())));
        assertThat(newReferral.getBeneficiaryCode(), is(stepsovcCase.getBeneficiary_code()));
        assertThat(newReferral.getCondoms().isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getCondoms())));
        assertThat(newReferral.getEndOfLifeHospice().isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getEnd_of_life_hospice())));
        assertThat(newReferral.getFacilityCode(), is(stepsovcCase.getFacility_code()));
        assertThat(newReferral.getFamilyPlanning().isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getFamily_planning())));
        assertThat(newReferral.getHivCounseling().isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getHiv_counseling())));
        assertThat(newReferral.getNewDiagnosis().isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getNew_diagnosis())));
        assertThat(newReferral.getOtherHealthServices().isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getOther_health_services())));
        assertThat(newReferral.getPainManagement().isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getPain_management())));
        assertThat(newReferral.getPmtct().isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getPmtct())));
        assertThat(newReferral.getServiceDate(), is(stepsovcCase.getService_date()));
        assertThat(newReferral.getSexuallyTransmittedInfection().isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getSexually_transmitted_infection())));
        assertThat(newReferral.getVisitDate(), is(stepsovcCase.getVisit_date()));
        assertThat(newReferral.getFollowupRequired(), is(stepsovcCase.getFollowup_required()));
        assertThat(newReferral.getFollowupDate(), is(stepsovcCase.getFollowup_date()));
    }

    public static StepsovcCase createCaseForReferral(String code, String serviceDate, String facilityId) {
        StepsovcCase stepsovcCase = createNewCase(code);
        stepsovcCase.setCondoms("Referred");
        stepsovcCase.setArt_adherence_counseling("Referred");
        stepsovcCase.setEnd_of_life_hospice("Not Referred");
        stepsovcCase.setFamily_planning("Referred");
        stepsovcCase.setFollowup_date("2012-12-12");
        stepsovcCase.setFollowup_required("Yes");
        stepsovcCase.setHiv_counseling("Referred");
        stepsovcCase.setPmtct("Referred");
        stepsovcCase.setNew_diagnosis("Referred");
        stepsovcCase.setSexually_transmitted_infection("Not Referred");
        stepsovcCase.setPain_management("Referred");
        stepsovcCase.setOther_health_services("Referred");
        stepsovcCase.setVisit_date("1988-12-23");
        stepsovcCase.setService_date(serviceDate);
        stepsovcCase.setFacility_code(facilityId);
        stepsovcCase.setUser_id("Userid001");

        return stepsovcCase;
    }

    public static StepsovcCase createNewCase(String code) {
        StepsovcCase stepsovcCase = new StepsovcCase();

        stepsovcCase.setCase_type(CaseType.BENEFICIARY_CASE.getType());
        stepsovcCase.setBeneficiary_code(code);
        stepsovcCase.setBeneficiary_dob("10-10-1967");
        stepsovcCase.setBeneficiary_name("Ben_Name");
        stepsovcCase.setCaregiver_code("CareGiverID");
        stepsovcCase.setCaregiver_name("CareGiverName");
        return stepsovcCase;
    }

    @Test
    public void shouldUpdateService() throws ParseException {
        StepsovcCase stepsovcCase = createCaseForUpdateService("Ben_Code", "2012-4-12");

        Referral newReferral = new ReferralMapper().map(stepsovcCase);

        newReferral = new ReferralMapper().updateServices(newReferral, stepsovcCase);

        assertServices(stepsovcCase, newReferral);

    }

    public static void assertServices(StepsovcCase stepsovcCase, Referral newReferral) {
        assertThat(newReferral.getArtAdherenceCounseling().isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getArt_adherence_counseling())));
        assertThat(newReferral.getCondoms().isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getCondoms())));
        assertThat(newReferral.getEndOfLifeHospice().isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getEnd_of_life_hospice())));
        assertThat(newReferral.getFamilyPlanning().isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getFamily_planning())));
        assertThat(newReferral.getHivCounseling().isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getHiv_counseling())));
        assertThat(newReferral.getNewDiagnosis().isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getNew_diagnosis())));
        assertThat(newReferral.getOtherHealthServices().isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getOther_health_services())));
        assertThat(newReferral.getPainManagement().isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getPain_management())));
        assertThat(newReferral.getPmtct().isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getPmtct())));
        assertThat(newReferral.getSexuallyTransmittedInfection().isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getSexually_transmitted_infection())));

        assertThat(newReferral.getFacilityCode(), is(stepsovcCase.getFacility_code()));
        assertThat(newReferral.getServiceDate(), is(stepsovcCase.getService_date()));
    }

    @Test
    public void shouldUpdateReferralWithReason() throws ParseException {
        StepsovcCase stepsovcCase = createCaseForUpdateReferral("Ben_Code");

        Referral newReferral = new ReferralMapper().map(stepsovcCase);

        newReferral = new ReferralMapper().updateReferral(newReferral, stepsovcCase);

        assertReferralReasons(stepsovcCase, newReferral);
    }

    public static void assertReferralReasons(StepsovcCase stepsovcCase, Referral newReferral) {
        assertThat(newReferral.getArtAdherenceCounseling().getReason(), is(stepsovcCase.getArt_adherence_na_reason()));
        assertThat(newReferral.getCondoms().getReason(), is(stepsovcCase.getCondoms_na_reason()));
        assertThat(newReferral.getFamilyPlanning().getReason(), is(stepsovcCase.getFamily_planning_na_reason()));
        assertThat(newReferral.getHivCounseling().getReason(), is(stepsovcCase.getHiv_counseling_na_reason()));
        assertThat(newReferral.getNewDiagnosis().getReason(), is(stepsovcCase.getNew_diagnosis_na_reason()));
        assertThat(newReferral.getOtherHealthServices().getReason(), is(stepsovcCase.getOther_health_service_na_reason()));
        assertThat(newReferral.getPainManagement().getReason(), is(stepsovcCase.getPain_management_na_reason()));
        assertThat(newReferral.getEndOfLifeHospice().getReason(), is(stepsovcCase.getEnd_of_life_hospice_na_reason()));
        assertThat(newReferral.getSexuallyTransmittedInfection().getReason(), is(stepsovcCase.getSexually_transmitted_na_reason()));
        assertThat(newReferral.getPmtct().getReason(), is(stepsovcCase.getPmtct_na_reason()));
    }

    public static StepsovcCase createCaseForUpdateReferral(String beneficiaryCode) {
        StepsovcCase stepsovcCase = createNewCase(beneficiaryCode);
        stepsovcCase.setArt_adherence_na_reason("Art_adherence_na_reason");
        stepsovcCase.setCondoms_na_reason("Condoms_na_reason");
        stepsovcCase.setEnd_of_life_hospice_na_reason("End_of_life_hospice_na_reason");
        stepsovcCase.setFamily_planning_na_reason("Family_planning_na_reason");
        stepsovcCase.setHiv_counseling_na_reason("setHiv_counseling_na_reason");
        stepsovcCase.setNew_diagnosis_na_reason("setNew_diagnosis_na_reason");
        stepsovcCase.setSexually_transmitted_na_reason("setSexually_transmitted_na_reason");
        stepsovcCase.setPain_management_na_reason("setPain_management_na_reason");
        stepsovcCase.setPmtct_na_reason("setPmtct_na_reason");
        stepsovcCase.setOther_health_service_na_reason("setOther_health_service_na_reason");
        return stepsovcCase;

    }

    public static StepsovcCase createCaseForUpdateService(String code, String visitDate) {
        StepsovcCase stepsovcCase = createCaseForReferral(code, null, "FAC001");
        stepsovcCase.setCondoms("Received");
        stepsovcCase.setEnd_of_life_hospice("Not Availed");
        stepsovcCase.setFamily_planning("Received");
        stepsovcCase.setFollowup_date("2012-12-12");
        stepsovcCase.setFollowup_required("Yes");
        stepsovcCase.setHiv_counseling("Received");
        stepsovcCase.setPmtct("Received");
        stepsovcCase.setNew_diagnosis("Received");
        stepsovcCase.setSexually_transmitted_infection("Not Availed");
        stepsovcCase.setPain_management("Received");
        stepsovcCase.setOther_health_services("Received");
        stepsovcCase.setArt_adherence_counseling("Received");
        stepsovcCase.setFacility_code("ABC");
        stepsovcCase.setService_date(null);
        stepsovcCase.setVisit_date(visitDate);

        return stepsovcCase;
    }


}
