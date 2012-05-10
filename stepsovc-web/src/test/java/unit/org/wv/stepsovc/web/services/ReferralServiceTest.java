package org.wv.stepsovc.web.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.vo.BeneficiaryFormRequest;
import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.mapper.ReferralMapper;
import org.wv.stepsovc.web.mapper.ReferralMapperTest;
import org.wv.stepsovc.web.repository.AllReferrals;
import org.wv.stepsovc.web.request.StepsovcCase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ReferralServiceTest {
    @Mock
    AllReferrals allReferrals;
    @Mock
    ReferralService referralService;

    @Mock
    CommcareGateway commcareGateway;

    @Before
    public void setup() {
        initMocks(this);
        referralService = spy(new ReferralService(""));
        ReflectionTestUtils.setField(referralService, "allReferrals", allReferrals);
        ReflectionTestUtils.setField(referralService, "commcareGateway", commcareGateway);
    }

    @Test
    public void shouldUpdateReferralOwnerWhileCreatingNewReferral(){

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        ArgumentCaptor<StepsovcCase> updatedBeneficiary = ArgumentCaptor.forClass(StepsovcCase.class);

        String code = "ben001";
        String groupId = "group001";

        StepsovcCase stepsovcCase = ReferralMapperTest.createCaseForReferral(code, "31-05-2012");

        doReturn(groupId).when(commcareGateway).getGroupId(stepsovcCase.getService_provider());
        doReturn(null).when(allReferrals).findActiveReferral(code);


        referralService.addNewReferral(stepsovcCase);

        verify(allReferrals).add(referralArgumentCaptor.capture());

        doNothing().when(allReferrals).add(referralArgumentCaptor.getValue());
        verify(commcareGateway).getGroupId(stepsovcCase.getService_provider());

        verify(referralService).updateReferralOwner(updatedBeneficiary.capture());

        doNothing().when(commcareGateway).updateReferralOwner(
                anyString(), Matchers.<BeneficiaryFormRequest>any());

        assertThat(updatedBeneficiary.getValue().getOwner_id(), is(stepsovcCase.getUser_id() + "," + groupId));

    }

    @Test
    public void shouldRemoveFacilityFromOwnersWhileUpdatingReferralServices(){

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        ArgumentCaptor<StepsovcCase> updatedBeneficiary = ArgumentCaptor.forClass(StepsovcCase.class);

        String code = "ben001";
        String groupId = "group001";

        StepsovcCase stepsovcCase = ReferralMapperTest.createCaseForUpdateService(code, "31-05-2012");
        String ownerId = "userid" + "," + groupId;
        stepsovcCase.setOwner_id(ownerId);
        stepsovcCase.setService_provider(null);

        Referral referral = new ReferralMapper().map(stepsovcCase);

        doReturn(referral).when(allReferrals).findActiveReferral(code);

        referralService.updateAvailedServices(stepsovcCase);

        verify(allReferrals).update(referralArgumentCaptor.capture());

        doNothing().when(allReferrals).update(referralArgumentCaptor.getValue());

        verify(referralService).removeFromCurrentFacility(updatedBeneficiary.capture());

        doNothing().when(commcareGateway).updateReferralOwner(
                anyString(), Matchers.<BeneficiaryFormRequest>any());

        assertThat(updatedBeneficiary.getValue().getOwner_id(), is(stepsovcCase.getUser_id()));
    }

    @Test
    public void shouldAssignToNewFacilityWhileUpdatingReferralServicesWithServiceProvider(){

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        ArgumentCaptor<StepsovcCase> updatedBeneficiary = ArgumentCaptor.forClass(StepsovcCase.class);

        String code = "ben001";
        String groupId1 = "group001";
        String groupId2 = "group002";

        StepsovcCase stepsovcCase = ReferralMapperTest.createCaseForUpdateService(code, "31-05-2012");
        String ownerId = "userid" + "," + groupId1;
        stepsovcCase.setOwner_id(ownerId);

        String groupName = "groupName";
        stepsovcCase.setService_provider(groupName);

        doReturn(groupId2).when(commcareGateway).getGroupId(groupName);

        Referral referral = new ReferralMapper().map(stepsovcCase);

        doReturn(referral).when(allReferrals).findActiveReferral(code);

        referralService.updateAvailedServices(stepsovcCase);

        verify(allReferrals).update(referralArgumentCaptor.capture());

        doNothing().when(allReferrals).update(referralArgumentCaptor.getValue());

        verify(referralService).assignToFacility(updatedBeneficiary.capture());

        doNothing().when(commcareGateway).updateReferralOwner(
                anyString(), Matchers.<BeneficiaryFormRequest>any());

        assertThat(updatedBeneficiary.getValue().getOwner_id(), is(stepsovcCase.getUser_id()+","+groupId2));
    }

    @Test
    public void shouldDeactivatePreviousReferralWhileCreatingNew() {
        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);

        String code = "ben001";

        StepsovcCase stepsovcCase = ReferralMapperTest.createCaseForReferral(code,"31-05-2012");

        doReturn(new Referral()).when(allReferrals).findActiveReferral(code);
        doNothing().when(allReferrals).add(Matchers.<Referral>any());
        doNothing().when(allReferrals).update(Matchers.<Referral>any());

        referralService.addNewReferral(stepsovcCase);

        verify(allReferrals).update(referralArgumentCaptor.capture());

        assertFalse(referralArgumentCaptor.getValue().isActive());
    }

}
