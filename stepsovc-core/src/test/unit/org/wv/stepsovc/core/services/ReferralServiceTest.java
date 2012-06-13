package org.wv.stepsovc.core.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.BeneficiaryInformation;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.fixtures.StepsovcCaseFixture;
import org.wv.stepsovc.core.mapper.ReferralMapper;
import org.wv.stepsovc.core.repository.AllReferrals;
import org.wv.stepsovc.core.request.StepsovcCase;
import org.wv.stepsovc.core.vo.FacilityAvailability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ReferralServiceTest {
    @Mock
    AllReferrals mockAllReferrals;
    @Mock
    ReferralService referralService;
    @Mock
    FacilityService facilityService;
    @Mock
    CommcareGateway commcareGateway;
    @Mock
    StepsovcScheduleService mockStepsovcScheduleService;

    @Before
    public void setup() {
        initMocks(this);
        referralService = spy(new ReferralService());
        ReflectionTestUtils.setField(referralService, "allReferrals", mockAllReferrals);
        ReflectionTestUtils.setField(referralService, "commcareGateway", commcareGateway);
        ReflectionTestUtils.setField(referralService, "facilityService", facilityService);
        ReflectionTestUtils.setField(referralService, "stepsovcScheduleService", mockStepsovcScheduleService);
    }

    @Test
    public void shouldSendSMSToFacility_CreateSchedulesAndUpdateReferralOwnerWhileCreatingNewReferral() {

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        ArgumentCaptor<BeneficiaryInformation> updatedBeneficiary = ArgumentCaptor.forClass(BeneficiaryInformation.class);

        String code = "ben001";
        String facilityCode = "FAC001";

        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForReferral(code, "2012-05-30", "FAC001");

        doReturn(null).when(mockAllReferrals).findActiveReferral(code);
        doReturn(new FacilityAvailability(true, null)).when(facilityService).getFacilityAvailability(stepsovcCase.getFacility_code(), stepsovcCase.getService_date());

        referralService.addNewReferral(stepsovcCase);

        verify(mockAllReferrals).add(referralArgumentCaptor.capture());
        verify(mockStepsovcScheduleService).scheduleNewReferral(referralArgumentCaptor.getValue());

        doNothing().when(mockAllReferrals).add(referralArgumentCaptor.getValue());

        ArgumentCaptor<String> facilityCodeCaptor = ArgumentCaptor.forClass(String.class);
        verify(commcareGateway).addGroupOwnership(updatedBeneficiary.capture(), facilityCodeCaptor.capture());

        assertThat(facilityCodeCaptor.getValue(), is(facilityCode));
        assertThat(updatedBeneficiary.getValue().getCareGiverId(), is(stepsovcCase.getUser_id()));

    }

    @Test
    public void shouldMoveReferralToAvailableDateIfFacilityIsUnavailable() {

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);

        String code = "ben001";

        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForReferral(code, "2012-05-30", "FAC001");

        doReturn(null).when(mockAllReferrals).findActiveReferral(code);
        doReturn(new FacilityAvailability(false, "2012-06-01")).when(facilityService).getFacilityAvailability(stepsovcCase.getFacility_code(), stepsovcCase.getService_date());

        referralService.addNewReferral(stepsovcCase);

        verify(mockAllReferrals).add(referralArgumentCaptor.capture());
        verify(mockStepsovcScheduleService).scheduleNewReferral(referralArgumentCaptor.getValue());

        doNothing().when(mockAllReferrals).add(referralArgumentCaptor.getValue());

        assertThat(referralArgumentCaptor.getValue().getServiceDate(), is("2012-06-01"));

    }

    @Test
    public void shouldRemoveFacilityFromOwnersAndUnScheduleAppointmentsWhileUpdatingReferralServices() {

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        ArgumentCaptor<BeneficiaryInformation> updatedBeneficiary = ArgumentCaptor.forClass(BeneficiaryInformation.class);

        String code = "ben001";
        String groupId = "group001";

        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForUpdateService(code, "2012-05-31");
        String ownerId = "userid" + "," + groupId;
        stepsovcCase.setOwner_id(ownerId);
        stepsovcCase.setFacility_code(null);

        Referral referral = new ReferralMapper().map(stepsovcCase);

        doReturn(referral).when(mockAllReferrals).findActiveReferral(code);

        referralService.updateAvailedServices(stepsovcCase);

        verify(mockAllReferrals).update(referralArgumentCaptor.capture());

        doNothing().when(mockAllReferrals).update(referralArgumentCaptor.getValue());

        ArgumentCaptor<String> facilityCodeCaptor = ArgumentCaptor.forClass(String.class);
        verify(commcareGateway).removeGroupOwnership(updatedBeneficiary.capture(), facilityCodeCaptor.capture());
        verify(mockStepsovcScheduleService).unscheduleReferral(referral.getOvcId());

        assertNull(facilityCodeCaptor.getValue());
        assertThat(updatedBeneficiary.getValue().getCareGiverId(), is(stepsovcCase.getUser_id()));
    }

    @Test
    public void shouldAssignToNewFacilityAndRescheduleAppointmentWhileUpdatingReferralServicesWithServiceProvider() {

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        ArgumentCaptor<BeneficiaryInformation> updatedBeneficiary = ArgumentCaptor.forClass(BeneficiaryInformation.class);

        String code = "ben001";
        String groupId1 = "group001";
        String groupId2 = "group002";

        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForUpdateService(code, "2012-05-31");
        String ownerId = "userid" + "," + groupId1;
        stepsovcCase.setOwner_id(ownerId);

        String groupName = "groupName";
        stepsovcCase.setFacility_code(groupName);
        Referral referral = new ReferralMapper().map(stepsovcCase);

        doReturn(referral).when(mockAllReferrals).findActiveReferral(code);
        doReturn(new FacilityAvailability(true, null)).when(facilityService).getFacilityAvailability(referral.getFacilityCode(), referral.getServiceDate());

        referralService.updateAvailedServices(stepsovcCase);

        verify(mockAllReferrals).update(referralArgumentCaptor.capture());
        verify(mockStepsovcScheduleService).unscheduleReferral(referral.getOvcId());
        verify(mockStepsovcScheduleService).scheduleNewReferral(referralArgumentCaptor.getValue());

        doNothing().when(mockAllReferrals).update(referralArgumentCaptor.getValue());

        ArgumentCaptor<String> facilityCodeCaptor = ArgumentCaptor.forClass(String.class);
        verify(commcareGateway).addGroupOwnership(updatedBeneficiary.capture(), facilityCodeCaptor.capture());

        assertThat(facilityCodeCaptor.getValue(), is(groupName));
        assertThat(updatedBeneficiary.getValue().getCareGiverId(), is(stepsovcCase.getUser_id()));
    }

    @Test
    public void shouldDeactivatePreviousReferralWhileCreatingNewAndRemoveOldSchedules() {
        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);

        String code = "ben001";
        String oldOvcId = "oooVcId";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForReferral(code, "2012-05-31", "FAC001");

        doReturn(new Referral().setOvcId(oldOvcId)).when(mockAllReferrals).findActiveReferral(code);
        doNothing().when(mockAllReferrals).add(Matchers.<Referral>any());
        doNothing().when(mockAllReferrals).update(Matchers.<Referral>any());
        doReturn(new FacilityAvailability(true, null)).when(facilityService).getFacilityAvailability(stepsovcCase.getFacility_code(), stepsovcCase.getService_date());

        referralService.addNewReferral(stepsovcCase);

        verify(mockAllReferrals).update(referralArgumentCaptor.capture());
        verify(mockStepsovcScheduleService).unscheduleReferral(referralArgumentCaptor.getValue().getOvcId());
        verify(mockStepsovcScheduleService).unscheduleFollowUpVisit(referralArgumentCaptor.getValue().getOvcId());

        assertFalse(referralArgumentCaptor.getValue().isActive());
    }

    @Test
    public void shouldUpdateServiceDateForReferralsWhichFallOnServiceUnavailableDates() {
        String facilityId = "123";
        String fromDateStr = "2012-05-01";
        String toDateStr = "2012-05-04";
        String nextAvailDate = "2012-05-05";

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        doReturn(Arrays.asList(new Referral().setOvcId("ovc1").setServiceDate(fromDateStr), new Referral().setOvcId("ovc2").setServiceDate(fromDateStr))).when(mockAllReferrals).findActiveReferrals(facilityId, fromDateStr);
        doReturn(Arrays.asList(new Referral().setOvcId("ovc3").setServiceDate(fromDateStr))).when(mockAllReferrals).findActiveReferrals(facilityId, "2012-05-02");
        doReturn(new ArrayList<Referral>()).when(mockAllReferrals).findActiveReferrals(facilityId, "2012-05-03");
        doReturn(Arrays.asList(new Referral().setOvcId("ovc4").setServiceDate(fromDateStr))).when(mockAllReferrals).findActiveReferrals(facilityId, toDateStr);

        List<Referral> updatedReferrals = referralService.updateReferralsServiceDate(facilityId, fromDateStr, toDateStr, nextAvailDate);

        assertThat(updatedReferrals.size(), is(4));
        verify(mockAllReferrals, times(4)).update(referralArgumentCaptor.capture());

        for (Referral referral : referralArgumentCaptor.getAllValues()) {
            verify(mockStepsovcScheduleService).unscheduleReferral(referral.getOvcId());
            assertThat(referral.getServiceDate(), is(nextAvailDate));
        }

    }

    @Test
    public void shouldUpdateUnAvailedReasons() {
        String benCode = "ABC";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createNewCase(benCode);
        Referral toBeReturned = new ReferralMapper().map(stepsovcCase);
        stepsovcCase = StepsovcCaseFixture.createCaseForUpdateReferral(benCode);
        stepsovcCase.setBeneficiary_code(benCode);
        String ovcId = "1234";
        toBeReturned.setOvcId(ovcId);
        doReturn(toBeReturned).when(mockAllReferrals).findActiveReferral(benCode);
        referralService.updateNotAvailedReasons(stepsovcCase);
        verify(mockStepsovcScheduleService).unscheduleReferral(toBeReturned.getOvcId());
        verify(mockAllReferrals).update(toBeReturned);

    }

}
