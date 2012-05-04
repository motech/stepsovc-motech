package org.wv.stepsovc.web.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.repository.AllGroups;
import org.wv.stepsovc.vo.BeneficiaryFormRequest;
import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.mapper.ReferralMapperTest;
import org.wv.stepsovc.web.repository.AllReferrals;
import org.wv.stepsovc.web.request.BeneficiaryCase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ReferralServiceTest {
    @Mock
    AllReferrals allReferrals;
    @Mock
    AllGroups allGroups;
    @Mock
    ReferralService referralService;

    @Mock
    CommcareGateway commcareGateway;

    @Before
    public void setup() {
        initMocks(this);
        referralService = spy(new ReferralService(""));
        ReflectionTestUtils.setField(referralService, "allReferrals", allReferrals);
        ReflectionTestUtils.setField(referralService, "allGroups", allGroups);
        ReflectionTestUtils.setField(referralService, "commcareGateway", commcareGateway);
    }

    @Test
    public void shouldUpdateReferralOwnerWhileCreatingNewReferral(){

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        ArgumentCaptor<BeneficiaryCase> updatedBeneficiary = ArgumentCaptor.forClass(BeneficiaryCase.class);

        String code = "ben001";
        String groupId = "group001";

        BeneficiaryCase beneficiaryCase = ReferralMapperTest.createCaseForReferral(code, "31-05-2012");

        doReturn(groupId).when(allGroups).getIdByName(beneficiaryCase.getService_provider());
        doReturn(null).when(allReferrals).findActiveReferral(code);


        referralService.addNewReferral(beneficiaryCase);

        verify(allReferrals).add(referralArgumentCaptor.capture());

        doNothing().when(allReferrals).add(referralArgumentCaptor.getValue());
        verify(allGroups).getIdByName(beneficiaryCase.getService_provider());

        verify(referralService).updateReferralOwner(updatedBeneficiary.capture());

        doNothing().when(commcareGateway).submitOwnerUpdateForm(
                anyString(), Matchers.<BeneficiaryFormRequest>any());

        assertThat(updatedBeneficiary.getValue().getOwner_id(), is(beneficiaryCase.getUser_id() + "," + groupId));

    }

    @Test
    public void shouldDeactivatePreviousReferralWhileCreatingNew() {
        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);

        String code = "ben001";

        BeneficiaryCase beneficiaryCase = ReferralMapperTest.createCaseForReferral(code,"31-05-2012");

        doReturn(new Referral()).when(allReferrals).findActiveReferral(code);
        doNothing().when(allReferrals).add(Matchers.<Referral>any());
        doNothing().when(allReferrals).update(Matchers.<Referral>any());

        referralService.addNewReferral(beneficiaryCase);

        verify(allReferrals).update(referralArgumentCaptor.capture());

        assertFalse(referralArgumentCaptor.getValue().isActive());
    }

}
