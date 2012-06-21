package fixtures;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.wv.stepsovc.commcare.domain.CaseType;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.Service;
import org.wv.stepsovc.core.mapper.ReferralMapper;
import org.wv.stepsovc.core.request.ServiceUnavailedType;
import org.wv.stepsovc.core.request.StepsovcCase;

import java.text.ParseException;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.wv.stepsovc.core.domain.ServiceType.*;
import static org.wv.stepsovc.core.mapper.ReferralMapper.SERVICE_RECEIVED;

public class StepsovcCaseFixture {

    public static void assertReferrals(StepsovcCase stepsovcCase, Referral newReferral) {
        Map<String, Service> referredServices = newReferral.getReferredServices();
        assertThat(referredServices.get(ART_ADHERENCE.getCode()).isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getArt_adherence_counseling())));
        assertThat(newReferral.getBeneficiaryCode(), is(stepsovcCase.getBeneficiary_code()));
        assertThat(referredServices.get(CONDOMS.getCode()).isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getCondoms())));
        assertThat(referredServices.get(END_OF_LIFE_HOSPICE.getCode()).isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getEnd_of_life_hospice())));
        assertThat(newReferral.getFacilityCode(), is(stepsovcCase.getFacility_code()));
        assertThat(referredServices.get(FAMILY_PLANNING.getCode()).isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getFamily_planning())));
        assertThat(referredServices.get(HIV_COUNSELING.getCode()).isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getHiv_counseling())));
        assertThat(referredServices.get(NEW_DIAGNOSIS.getCode()).isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getNew_diagnosis())));
        assertThat(referredServices.get(OTHER_HEALTH_SERVICES.getCode()).isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getOther_health_services())));
        assertThat(referredServices.get(PAIN_MANAGEMENT.getCode()).isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getPain_management())));
        assertThat(referredServices.get(PMTCT.getCode()).isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getPmtct())));
        assertThat(newReferral.getServiceDate(), is(stepsovcCase.getService_date()));
        assertThat(referredServices.get(SEXUALLY_TRANSMITTED_INFEC.getCode()).isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getSexually_transmitted_infection())));
        assertThat(newReferral.getVisitDate(), is(stepsovcCase.getVisit_date()));
        assertThat(newReferral.getFollowupRequired(), is(stepsovcCase.getFollowup_required()));
        assertThat(newReferral.getFollowupDate(), is(stepsovcCase.getFollowup_date()));
    }

    public static StepsovcCase createCaseForReferral(String beneficiaryCode, String serviceDate, String facilityId) {
        StepsovcCase stepsovcCase = createNewBeneficiaryCase(beneficiaryCode);
        stepsovcCase.setCondoms("Referred");
        stepsovcCase.setArt_adherence_counseling("Referred");
        stepsovcCase.setEnd_of_life_hospice("Not Referred");
        stepsovcCase.setFamily_planning("Referred");
        stepsovcCase.setFollowup_date("2012-12-12");
        stepsovcCase.setFollowup_required("yes");
        stepsovcCase.setHiv_counseling("Referred");
        stepsovcCase.setPmtct("Referred");
        stepsovcCase.setNew_diagnosis("Referred");
        stepsovcCase.setSexually_transmitted_infection("Not Referred");
        stepsovcCase.setPain_management("Referred");
        stepsovcCase.setOther_health_services("Referred");
        stepsovcCase.setVisit_date("1988-12-23");
        stepsovcCase.setService_date(serviceDate);
        stepsovcCase.setService_details("new service details");
        stepsovcCase.setFacility_code(facilityId);
        stepsovcCase.setUser_id("Userid001");

        return stepsovcCase;
    }

    public static StepsovcCase createNewBeneficiaryCase(String beneficiaryCode) {
        StepsovcCase stepsovcCase = new StepsovcCase();

        stepsovcCase.setCase_type(CaseType.BENEFICIARY_CASE.getType());
        stepsovcCase.setBeneficiary_code(beneficiaryCode);
        stepsovcCase.setBeneficiary_dob("10-10-1967");
        stepsovcCase.setBeneficiary_name("Ben_Name");
        stepsovcCase.setCaregiver_code("CareGiverID");
        stepsovcCase.setCaregiver_name("CareGiverName");
        stepsovcCase.setCase_id("BenId");
        return stepsovcCase;
    }

    @Test
    public void shouldUpdateService() throws ParseException {
        StepsovcCase stepsovcCase = createCaseForUpdateService("Ben_Code", "2012-4-12", "ABC");

        Referral newReferral = new ReferralMapper().map(stepsovcCase);

        newReferral = new ReferralMapper().updateServices(newReferral, stepsovcCase);

        assertServices(stepsovcCase, newReferral);

    }

    public static void assertServices(StepsovcCase stepsovcCase, Referral newReferral) {
        Map<String, Service> referredServices = newReferral.getReferredServices();
        assertThat(referredServices.get(ART_ADHERENCE.getCode()).isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getArt_adherence_counseling())));
        assertThat(referredServices.get(CONDOMS.getCode()).isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getCondoms())));
        assertThat(referredServices.get(END_OF_LIFE_HOSPICE.getCode()).isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getEnd_of_life_hospice())));
        assertThat(referredServices.get(FAMILY_PLANNING.getCode()).isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getFamily_planning())));
        assertThat(referredServices.get(HIV_COUNSELING.getCode()).isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getHiv_counseling())));
        assertThat(referredServices.get(NEW_DIAGNOSIS.getCode()).isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getNew_diagnosis())));
        assertThat(referredServices.get(OTHER_HEALTH_SERVICES.getCode()).isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getOther_health_services())));
        assertThat(referredServices.get(PAIN_MANAGEMENT.getCode()).isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getPain_management())));
        assertThat(referredServices.get(PMTCT.getCode()).isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getPmtct())));
        assertThat(referredServices.get(SEXUALLY_TRANSMITTED_INFEC.getCode()).isProvided(), is(ReferralMapper.SERVICE_RECEIVED.equals(stepsovcCase.getSexually_transmitted_infection())));

        assertThat(newReferral.getServiceDetails(), is(stepsovcCase.getService_details()));
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
        Map<String, Service> referredServices = newReferral.getReferredServices();
        assertThat(referredServices.get(ART_ADHERENCE.getCode()).getReason(), is(serviceUnavailedReason(stepsovcCase.getArt_adherence_na_reason())));
        assertThat(referredServices.get(CONDOMS.getCode()).getReason(), is(serviceUnavailedReason(stepsovcCase.getCondoms_na_reason())));
        assertThat(referredServices.get(FAMILY_PLANNING.getCode()).getReason(), is(serviceUnavailedReason(stepsovcCase.getFamily_planning_na_reason())));
        assertThat(referredServices.get(HIV_COUNSELING.getCode()).getReason(), is(serviceUnavailedReason(stepsovcCase.getHiv_counseling_na_reason())));
        assertThat(referredServices.get(NEW_DIAGNOSIS.getCode()).getReason(), is(serviceUnavailedReason(stepsovcCase.getNew_diagnosis_na_reason())));
        assertThat(referredServices.get(OTHER_HEALTH_SERVICES.getCode()).getReason(), is(serviceUnavailedReason(stepsovcCase.getOther_health_service_na_reason())));
        assertThat(referredServices.get(PAIN_MANAGEMENT.getCode()).getReason(), is(serviceUnavailedReason(stepsovcCase.getPain_management_na_reason())));
        assertThat(referredServices.get(END_OF_LIFE_HOSPICE.getCode()).getReason(), is(serviceUnavailedReason(stepsovcCase.getEnd_of_life_hospice_na_reason())));
        assertThat(referredServices.get(SEXUALLY_TRANSMITTED_INFEC.getCode()).getReason(), is(serviceUnavailedReason(stepsovcCase.getSexually_transmitted_na_reason())));
        assertThat(referredServices.get(PMTCT.getCode()).getReason(), is(serviceUnavailedReason(stepsovcCase.getPmtct_na_reason())));
    }

    public static StepsovcCase createCaseForUpdateReferral(String beneficiaryCode) {
        StepsovcCase stepsovcCase = createNewBeneficiaryCase(beneficiaryCode);
        stepsovcCase.setArt_adherence_na_reason("SERVICE_UNAVAILABLE");
        stepsovcCase.setCondoms_na_reason("FACILITY_CLOSED");
        stepsovcCase.setEnd_of_life_hospice_na_reason("BEN_UNABLE_TO_TRAVEL");
        stepsovcCase.setFamily_planning_na_reason("BEN_FORGOT");
        stepsovcCase.setHiv_counseling_na_reason("BEN_UNWILLING");
        stepsovcCase.setNew_diagnosis_na_reason("AVAILED_NOT_RECORED");
        stepsovcCase.setSexually_transmitted_na_reason("OTHER");
        stepsovcCase.setPain_management_na_reason("");
        stepsovcCase.setPmtct_na_reason("");
        stepsovcCase.setOther_health_service_na_reason("BEN_UNABLE_TO_TRAVEL");
        return stepsovcCase;

    }

    public static StepsovcCase createCaseForUpdateService(String code, String serviceDate, String facility_code) {
        StepsovcCase stepsovcCase = createCaseForReferral(code, null, facility_code);
        stepsovcCase.setCondoms("Not Availed");
        stepsovcCase.setEnd_of_life_hospice("Not Availed");
        stepsovcCase.setFamily_planning("Not Availed");
        stepsovcCase.setFollowup_date("2012-12-12");
        stepsovcCase.setFollowup_required("Yes");
        stepsovcCase.setHiv_counseling("Not Availed");
        stepsovcCase.setPmtct("Not Availed");
        stepsovcCase.setNew_diagnosis("Not Availed");
        stepsovcCase.setSexually_transmitted_infection("Not Availed");
        stepsovcCase.setPain_management("Not Availed");
        stepsovcCase.setOther_health_services("Not Availed");
        stepsovcCase.setArt_adherence_counseling("Not Availed");
        stepsovcCase.setFacility_code(facility_code);
        stepsovcCase.setService_date(null);
        stepsovcCase.setService_date(serviceDate);

        return stepsovcCase;
    }

    public static StepsovcCase createCaseForUpdateServiceWithServicesFullfilled(String code, String serviceDate, String facilityId) {
        StepsovcCase stepsovcCase = createCaseForReferral(code, null, facilityId);
        stepsovcCase.setCondoms(SERVICE_RECEIVED);
        stepsovcCase.setEnd_of_life_hospice(SERVICE_RECEIVED);
        stepsovcCase.setFamily_planning(SERVICE_RECEIVED);
        stepsovcCase.setFollowup_date("2012-12-12");
        stepsovcCase.setFollowup_required("Yes");
        stepsovcCase.setHiv_counseling(SERVICE_RECEIVED);
        stepsovcCase.setPmtct(SERVICE_RECEIVED);
        stepsovcCase.setNew_diagnosis(SERVICE_RECEIVED);
        stepsovcCase.setSexually_transmitted_infection(SERVICE_RECEIVED);
        stepsovcCase.setPain_management(SERVICE_RECEIVED);
        stepsovcCase.setOther_health_services(SERVICE_RECEIVED);
        stepsovcCase.setArt_adherence_counseling(SERVICE_RECEIVED);
        stepsovcCase.setFacility_code(facilityId);
        stepsovcCase.setService_date(null);
        stepsovcCase.setService_date(serviceDate);

        return stepsovcCase;
    }

    private static String serviceUnavailedReason(String key) {
        return StringUtils.isNotEmpty(key) ? ServiceUnavailedType.valueOf(key).getValue() : "";
    }

}
