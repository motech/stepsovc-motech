package org.wv.stepsovc.core.services;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.motechproject.appointments.api.service.contract.CreateVisitRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.BeneficiaryInformation;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.mapper.ReferralMapper;
import org.wv.stepsovc.core.mapper.ReferralMapperTest;
import org.wv.stepsovc.core.repository.AllAppointments;
import org.wv.stepsovc.core.repository.AllReferrals;
import org.wv.stepsovc.core.request.StepsovcCase;
import org.wv.stepsovc.core.vo.FacilityAvailability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.wv.stepsovc.core.factories.VisitRequestFactory.createVisitRequestForReferral;
import static org.wv.stepsovc.core.utils.DateUtils.getDateTime;

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
    AllAppointments mockAllAppointments;

    @Before
    public void setup() {
        initMocks(this);
        referralService = spy(new ReferralService());
        ReflectionTestUtils.setField(referralService, "allReferrals", mockAllReferrals);
        ReflectionTestUtils.setField(referralService, "commcareGateway", commcareGateway);
        ReflectionTestUtils.setField(referralService, "facilityService", facilityService);
        ReflectionTestUtils.setField(referralService, "allAppointments", mockAllAppointments);
    }

    @Test
    public void shouldUpdateReferralOwnerWhileCreatingNewReferral() {

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        ArgumentCaptor<StepsovcCase> updatedBeneficiary = ArgumentCaptor.forClass(StepsovcCase.class);

        String code = "ben001";
        String facilityCode = "FAC001";

        StepsovcCase stepsovcCase = ReferralMapperTest.createCaseForReferral(code, "2012-05-30", "FAC001");

        doReturn(null).when(mockAllReferrals).findActiveReferral(code);
        doReturn(new FacilityAvailability(true, null)).when(facilityService).getFacilityAvailability(stepsovcCase.getFacility_code(), stepsovcCase.getService_date());

        referralService.addNewReferral(stepsovcCase);

        verify(mockAllReferrals).add(referralArgumentCaptor.capture());

        DateTime appointmentDate = getDateTime(referralArgumentCaptor.getValue().getServiceDate());
        CreateVisitRequest visitRequestForReferral = createVisitRequestForReferral(referralArgumentCaptor.getValue().appointmentDataMap(), appointmentDate);
        ArgumentCaptor<CreateVisitRequest> visitRequestCaptor = ArgumentCaptor.forClass(CreateVisitRequest.class);
        String ovcId = referralArgumentCaptor.getValue().getOvcId();
        ArgumentCaptor<String> ovcIdCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockAllAppointments).add(ovcIdCaptor.capture(), visitRequestCaptor.capture());

        assertVisitRequest(visitRequestCaptor.getValue(), visitRequestForReferral);
        assertThat(ovcIdCaptor.getValue(),is(ovcId));

        doNothing().when(mockAllReferrals).add(referralArgumentCaptor.getValue());

        ArgumentCaptor<String> facilityCodeCaptor = ArgumentCaptor.forClass(String.class);
        verify(referralService).updateReferralOwner(updatedBeneficiary.capture(), facilityCodeCaptor.capture());

        assertThat(facilityCodeCaptor.getValue(), is(facilityCode));
        assertThat(updatedBeneficiary.getValue().getUser_id(), is(stepsovcCase.getUser_id()));

    }

    private void assertVisitRequest(CreateVisitRequest actual, CreateVisitRequest expected) {
        assertThat(actual.getAppointmentDueDate(), is(expected.getAppointmentDueDate()));
        assertThat(actual.getData(), is(expected.getData()));
        assertThat(actual.getTypeOfVisit(), is(expected.getTypeOfVisit()));
        assertThat(actual.getVisitName(), is(expected.getVisitName()));
    }

    @Test
    public void shouldMoveReferralToAvailableDateIfFacilityIsUnavailable() {

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);

        String code = "ben001";
        String groupId = "group001";

        StepsovcCase stepsovcCase = ReferralMapperTest.createCaseForReferral(code, "2012-05-30", "FAC001");

        doReturn(null).when(mockAllReferrals).findActiveReferral(code);
        doReturn(new FacilityAvailability(false, "2012-06-01")).when(facilityService).getFacilityAvailability(stepsovcCase.getFacility_code(), stepsovcCase.getService_date());

        referralService.addNewReferral(stepsovcCase);

        verify(mockAllReferrals).add(referralArgumentCaptor.capture());

        DateTime appointmentDate = getDateTime(referralArgumentCaptor.getValue().getServiceDate());
        CreateVisitRequest visitRequestForReferral = createVisitRequestForReferral(referralArgumentCaptor.getValue().appointmentDataMap(), appointmentDate);
        ArgumentCaptor<CreateVisitRequest> visitRequestCaptor = ArgumentCaptor.forClass(CreateVisitRequest.class);
        String ovcId = referralArgumentCaptor.getValue().getOvcId();
        ArgumentCaptor<String> ovcIdCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockAllAppointments).add(ovcIdCaptor.capture(), visitRequestCaptor.capture());
        assertVisitRequest(visitRequestCaptor.getValue(), visitRequestForReferral);
        assertThat(ovcIdCaptor.getValue(), is(ovcId));

        doNothing().when(mockAllReferrals).add(referralArgumentCaptor.getValue());

        assertThat(referralArgumentCaptor.getValue().getServiceDate(), is("2012-06-01"));

    }

    @Test
    public void shouldRemoveFacilityFromOwnersAndUnScheduleAppointmentsWhileUpdatingReferralServices() {

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        ArgumentCaptor<StepsovcCase> updatedBeneficiary = ArgumentCaptor.forClass(StepsovcCase.class);

        String code = "ben001";
        String groupId = "group001";

        StepsovcCase stepsovcCase = ReferralMapperTest.createCaseForUpdateService(code, "2012-05-31");
        String ownerId = "userid" + "," + groupId;
        stepsovcCase.setOwner_id(ownerId);
        stepsovcCase.setFacility_code(null);

        Referral referral = new ReferralMapper().map(stepsovcCase);

        String ovcId = referral.getOvcId();

        doReturn(referral).when(mockAllReferrals).findActiveReferral(code);

        referralService.updateAvailedServices(stepsovcCase);

        verify(mockAllReferrals).update(referralArgumentCaptor.capture());

        doNothing().when(mockAllReferrals).update(referralArgumentCaptor.getValue());

        ArgumentCaptor<String> facilityCodeCaptor = ArgumentCaptor.forClass(String.class);
        verify(referralService).updateReferralOwner(updatedBeneficiary.capture(), facilityCodeCaptor.capture());

        verify(mockAllAppointments).remove(ovcId);

        assertNull(facilityCodeCaptor.getValue());
        assertThat(updatedBeneficiary.getValue().getUser_id(), is(stepsovcCase.getUser_id()));
    }

    @Test
    public void shouldAssignToNewFacilityAndRescheduleAppointmentWhileUpdatingReferralServicesWithServiceProvider() {

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        ArgumentCaptor<StepsovcCase> updatedBeneficiary = ArgumentCaptor.forClass(StepsovcCase.class);

        String code = "ben001";
        String groupId1 = "group001";
        String groupId2 = "group002";

        StepsovcCase stepsovcCase = ReferralMapperTest.createCaseForUpdateService(code, "2012-05-31");
        String ownerId = "userid" + "," + groupId1;
        stepsovcCase.setOwner_id(ownerId);

        String groupName = "groupName";
        stepsovcCase.setFacility_code(groupName);
        Referral referral = new ReferralMapper().map(stepsovcCase);

        doReturn(referral).when(mockAllReferrals).findActiveReferral(code);
        doReturn(new FacilityAvailability(true, null)).when(facilityService).getFacilityAvailability(referral.getFacilityCode(), referral.getServiceDate());

        referralService.updateAvailedServices(stepsovcCase);

        verify(mockAllReferrals).update(referralArgumentCaptor.capture());
        verify(mockAllAppointments).remove(referralArgumentCaptor.getValue().getOvcId());

        DateTime appointmentDate = getDateTime(referralArgumentCaptor.getValue().getServiceDate());
        CreateVisitRequest visitRequestForReferral = createVisitRequestForReferral(referralArgumentCaptor.getValue().appointmentDataMap(), appointmentDate);
        ArgumentCaptor<CreateVisitRequest> visitRequestCaptor = ArgumentCaptor.forClass(CreateVisitRequest.class);
        String ovcId = referralArgumentCaptor.getValue().getOvcId();
        ArgumentCaptor<String> ovcIdCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockAllAppointments).add(ovcIdCaptor.capture(), visitRequestCaptor.capture());
        assertVisitRequest(visitRequestCaptor.getValue(), visitRequestForReferral);
        assertThat(ovcIdCaptor.getValue(), is(ovcId));

        doNothing().when(mockAllReferrals).update(referralArgumentCaptor.getValue());

        ArgumentCaptor<String> facilityCodeCaptor = ArgumentCaptor.forClass(String.class);
        verify(referralService).updateReferralOwner(updatedBeneficiary.capture(), facilityCodeCaptor.capture());

        assertThat(facilityCodeCaptor.getValue(), is(groupName));
        assertThat(updatedBeneficiary.getValue().getUser_id(), is(stepsovcCase.getUser_id()));
    }

    @Test
    public void shouldDeactivatePreviousReferralWhileCreatingNew() {
        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);

        String code = "ben001";
        String oldOvcId = "oooVcId";
        StepsovcCase stepsovcCase = ReferralMapperTest.createCaseForReferral(code, "2012-05-31", "FAC001");

        doReturn(new Referral().setOvcId(oldOvcId)).when(mockAllReferrals).findActiveReferral(code);
        doNothing().when(mockAllReferrals).add(Matchers.<Referral>any());
        doNothing().when(mockAllReferrals).update(Matchers.<Referral>any());
        doReturn(new FacilityAvailability(true, null)).when(facilityService).getFacilityAvailability(stepsovcCase.getFacility_code(), stepsovcCase.getService_date());

        referralService.addNewReferral(stepsovcCase);

        verify(mockAllReferrals).update(referralArgumentCaptor.capture());
        verify(mockAllAppointments).remove(referralArgumentCaptor.getValue().getOvcId());

        assertFalse(referralArgumentCaptor.getValue().isActive());
    }

    @Test
    public void shouldUpdateServiceDateForReferralsWhichFallOnServiceUnavailableDates() {
        String facilityId = "123";
        String fromDateStr = "2012-05-01";
        String toDateStr = "2012-05-04";
        String nextAvailDate = "2012-05-05";

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        doReturn(Arrays.asList(new Referral().setServiceDate(fromDateStr), new Referral().setServiceDate(fromDateStr))).when(mockAllReferrals).findActiveReferrals(facilityId, fromDateStr);
        doReturn(Arrays.asList(new Referral().setServiceDate(fromDateStr))).when(mockAllReferrals).findActiveReferrals(facilityId, "2012-05-02");
        doReturn(new ArrayList<Referral>()).when(mockAllReferrals).findActiveReferrals(facilityId, "2012-05-03");
        doReturn(Arrays.asList(new Referral().setServiceDate(fromDateStr))).when(mockAllReferrals).findActiveReferrals(facilityId, toDateStr);

        referralService.updateReferralsServiceDate(facilityId, fromDateStr, toDateStr, nextAvailDate);

        verify(mockAllReferrals, times(4)).update(referralArgumentCaptor.capture());

        DateTime appointmentDate = getDateTime(referralArgumentCaptor.getAllValues().get(0).getServiceDate());
        CreateVisitRequest visitRequestForReferral = createVisitRequestForReferral(referralArgumentCaptor.getValue().appointmentDataMap(), appointmentDate);
        ArgumentCaptor<CreateVisitRequest> visitRequestCaptor = ArgumentCaptor.forClass(CreateVisitRequest.class);
        String ovcId = referralArgumentCaptor.getValue().getOvcId();
        ArgumentCaptor<String> ovcIdCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockAllAppointments,times(4)).add(ovcIdCaptor.capture(), visitRequestCaptor.capture());
        for (CreateVisitRequest createVisitRequest : visitRequestCaptor.getAllValues()) {
            assertVisitRequest(createVisitRequest, visitRequestForReferral);
        }
        assertThat(ovcIdCaptor.getValue(), is(ovcId));

        List<Referral> actualReferrals = referralArgumentCaptor.getAllValues();
        for (Referral actualReferral : actualReferrals) {
            assertThat(actualReferral.getServiceDate(), is(nextAvailDate));
        }
    }

    @Test
    public void shouldUpdateUnAvailedReasons(){
        String benCode = "ABC";
        StepsovcCase stepsovcCase = ReferralMapperTest.createNewCase(benCode);
        Referral toBeReturned = new ReferralMapper().map(stepsovcCase);
        stepsovcCase = ReferralMapperTest.createCaseForUpdateReferral(benCode);
        stepsovcCase.setBeneficiary_code(benCode);
        String ovcId = "1234";
        toBeReturned.setOvcId(ovcId);
        doReturn(toBeReturned).when(mockAllReferrals).findActiveReferral(benCode);
        referralService.updateNotAvailedReasons(stepsovcCase);
        verify(mockAllAppointments).remove(ovcId);
        verify(mockAllReferrals).update(toBeReturned);

    }

}
