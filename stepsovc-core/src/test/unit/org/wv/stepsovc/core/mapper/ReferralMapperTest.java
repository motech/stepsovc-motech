package org.wv.stepsovc.core.mapper;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.Service;
import org.wv.stepsovc.core.fixtures.StepsovcCaseFixture;
import org.wv.stepsovc.core.request.ServiceUnavailedType;
import org.wv.stepsovc.core.request.StepsovcCase;

import java.text.ParseException;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.wv.stepsovc.core.domain.ServiceType.*;

public class ReferralMapperTest {

    @Test
    public void shouldMapBeneficiaryCaseToReferral() throws ParseException {
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForReferral("Ben_Code", "2012-4-12", "FAC001");
        Referral newReferral = null;

        newReferral = new ReferralMapper().map(stepsovcCase);
        assertReferrals(stepsovcCase, newReferral);
    }

    @Test
    public void shouldExtractReferredServices() throws Exception {
        Referral referral = new ReferralMapper().map(StepsovcCaseFixture.createCaseForReferral("Ben_Code", "2012-4-12", "FAC001"));
        assertThat(referral.referredServiceCodes().size(), is(8));
    }

    public static void assertReferrals(StepsovcCase stepsovcCase, Referral newReferral) {
        Map<String, Service> referredServices = newReferral.getReferredServices();
        assertThat(referredServices.get(ART_ADHERENCE.getCode()).isReferred(), is(ReferralMapper.REFERRED.equals(stepsovcCase.getArt_adherence_counseling())));
        assertThat(newReferral.getBeneficiaryCode(), is(stepsovcCase.getBeneficiary_code()));
        assertThat(newReferral.getCgId(), is(stepsovcCase.getUser_id()));
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

    @Test
    public void shouldUpdateService() throws ParseException {
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForUpdateService("Ben_Code", "2012-4-12");

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
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForUpdateReferral("Ben_Code");

        Referral newReferral = new ReferralMapper().map(stepsovcCase);

        newReferral = new ReferralMapper().updateReferral(newReferral, stepsovcCase);

        assertReferralReasons(stepsovcCase, newReferral);
    }

    @Test
    public void shouldRetainTheServiceProvidedStatus() throws Exception {
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForUpdateService("Ben_code", "2013-12-12");
        Referral newReferral = new ReferralMapper().map(stepsovcCase);
        stepsovcCase.setFamily_planning("Received");
        new ReferralMapper().updateServices(newReferral, stepsovcCase);
        stepsovcCase.setFamily_planning("Not Received");
        new ReferralMapper().updateServices(newReferral, stepsovcCase);
        assertTrue("Expected isProvided = true", newReferral.getReferredServices().get(FAMILY_PLANNING.getCode()).isProvided());
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

    private static String serviceUnavailedReason(String key) {
        return StringUtils.isNotEmpty(key) ? ServiceUnavailedType.valueOf(key).getValue() : "";
    }


}
