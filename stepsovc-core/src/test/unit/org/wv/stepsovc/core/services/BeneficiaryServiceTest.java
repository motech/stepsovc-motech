package org.wv.stepsovc.core.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.CaseOwnershipInformation;
import org.wv.stepsovc.core.domain.Beneficiary;
import org.wv.stepsovc.core.domain.Caregiver;
import org.wv.stepsovc.core.fixtures.StepsovcCaseFixture;
import org.wv.stepsovc.core.mapper.BeneficiaryMapper;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.repository.AllCaregivers;
import org.wv.stepsovc.core.request.StepsovcCase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class BeneficiaryServiceTest {

    private BeneficiaryService beneficiaryService;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    @Mock
    private CommcareGateway commcareGateway;
    @Mock
    private AllCaregivers mockAllCaregivers;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        beneficiaryService = new BeneficiaryService();
        ReflectionTestUtils.setField(beneficiaryService, "allBeneficiaries", allBeneficiaries);
        ReflectionTestUtils.setField(beneficiaryService, "commcareGateway", commcareGateway);
        ReflectionTestUtils.setField(beneficiaryService, "allCaregivers", mockAllCaregivers);
    }

    @Test
    public void shouldCreateBeneficiaryIfNotExists() throws Exception {
        String benCode = "BEN001";
        StepsovcCase stepsOvcCase = StepsovcCaseFixture.createNewCase(benCode);
        beneficiaryService.createBeneficiary(stepsOvcCase);

        ArgumentCaptor<Beneficiary> beneficiaryCaptor = ArgumentCaptor.forClass(Beneficiary.class);

        doReturn(null).when(allBeneficiaries).findBeneficiaryByCode(benCode);
        verify(allBeneficiaries).add(beneficiaryCaptor.capture());
        assertBeneficiary(beneficiaryCaptor.getValue(), new BeneficiaryMapper().map(stepsOvcCase));

    }

    @Test
    public void shouldRemoveBeneficiaryIfExistsAndCreateNewOne() throws Exception {
        String benCode = "BEN001";
        StepsovcCase stepsOvcCase = StepsovcCaseFixture.createNewCase(benCode);
        Beneficiary beneficiary = new Beneficiary();
        doReturn(beneficiary).when(allBeneficiaries).findBeneficiaryByCode(benCode);
        beneficiaryService.createBeneficiary(stepsOvcCase);

        ArgumentCaptor<Beneficiary> beneficiaryCaptor = ArgumentCaptor.forClass(Beneficiary.class);


        ArgumentCaptor<Beneficiary> oldBeneficiaryCaptor = ArgumentCaptor.forClass(Beneficiary.class);
        verify(allBeneficiaries).remove(oldBeneficiaryCaptor.capture());
        verify(allBeneficiaries).add(beneficiaryCaptor.capture());

        assertThat(oldBeneficiaryCaptor.getValue(), is(beneficiary));
        assertBeneficiary(beneficiaryCaptor.getValue(), new BeneficiaryMapper().map(stepsOvcCase));

    }

    @Test
    public void shouldNotAddUserOwnershipIfBeneficiaryNotExists() throws Exception {
        String benCode = "BEN001";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createNewCase(benCode);

        doReturn(null).when(allBeneficiaries).findBeneficiaryByCode(benCode);
        doReturn(new Caregiver()).when(mockAllCaregivers).findCaregiverByCode(stepsovcCase.getCaregiver_code());

        beneficiaryService.addUserOwnership(stepsovcCase);

        verifyZeroInteractions(commcareGateway);

    }

    @Test
    public void shouldNotAddUserOwnershipIfCaregiverNotExists() throws Exception {
        String benCode = "BEN001";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createNewCase(benCode);

        doReturn(new Beneficiary()).when(allBeneficiaries).findBeneficiaryByCode(benCode);
        doReturn(null).when(mockAllCaregivers).findCaregiverByCode(stepsovcCase.getCaregiver_code());

        beneficiaryService.addUserOwnership(stepsovcCase);

        verifyZeroInteractions(commcareGateway);

    }

    @Test
    public void shouldAddOwnershipIfBothCaregiverAndBeneficiaryExists() throws Exception {
        String benCode = "BEN001";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createNewCase(benCode);

        Caregiver caregiver = new Caregiver();
        caregiver.setFirstName("caregiver1");
        caregiver.setCgId("someCaregiverID");

        String caregiverId = "caregiverId";
        String caregiverCode = "caregiverCode";
        Caregiver requestingCaregiver = new Caregiver();
        requestingCaregiver.setCgId(caregiverId);

        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setCaregiverCode("CG1");
        beneficiary.setCaseId("SomecaseId");
        beneficiary.setCaregiverCode(caregiverCode);

        doReturn(beneficiary).when(allBeneficiaries).findBeneficiaryByCode(benCode);
        doReturn(requestingCaregiver).when(mockAllCaregivers).findCaregiverByCode(stepsovcCase.getCaregiver_code());
        doReturn(caregiver).when(mockAllCaregivers).findCaregiverByCode(caregiverCode);

        beneficiaryService.addUserOwnership(stepsovcCase);

        ArgumentCaptor<CaseOwnershipInformation> ownershipInformationCaptor = ArgumentCaptor.forClass(CaseOwnershipInformation.class);
        ArgumentCaptor<String> userIdCaptor = ArgumentCaptor.forClass(String.class);

        verify(commcareGateway).addUserOwnership(ownershipInformationCaptor.capture(), userIdCaptor.capture());

        assertThat(userIdCaptor.getValue(), is(caregiverId));
        assertCaseOwnershipInfo(beneficiary, caregiver, ownershipInformationCaptor);
    }

    @Test
    public void shouldNotAddGroupOwnershipIfBeneficiaryDoesNotExists() throws Exception {
        String benCode = "BenCode";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createNewCase(benCode);

        doReturn(null).when(allBeneficiaries).findBeneficiaryByCode(benCode);

        beneficiaryService.addGroupOwnership(stepsovcCase);

        verifyZeroInteractions(commcareGateway);
    }

    @Test
    public void shouldAddGroupOwnershipIfBeneficiaryExists() {
        String bencode = "BenCode";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createNewCase(bencode);
        stepsovcCase.setCaregiver_name("CareGiver");
        Beneficiary beneficiary = new Beneficiary();
        Caregiver caregiver = new Caregiver();
        beneficiary.setCaregiverCode("CG1");
        beneficiary.setCaseId("SomecaseId");
        caregiver.setFirstName("caregiver1");
        caregiver.setCgId("someCaregiverID");
        String caregiverCode = "CG1";
        beneficiary.setCaregiverCode(caregiverCode);
        doReturn(beneficiary).when(allBeneficiaries).findBeneficiaryByCode(bencode);
        doReturn(caregiver).when(mockAllCaregivers).findCaregiverByCode(caregiverCode);
        beneficiaryService.addGroupOwnership(stepsovcCase);
        ArgumentCaptor<CaseOwnershipInformation> beneficiaryInfoCaptor = ArgumentCaptor.forClass(CaseOwnershipInformation.class);
        ArgumentCaptor<String> groupNameCaptor = ArgumentCaptor.forClass(String.class);
        verify(commcareGateway).addGroupOwnership(beneficiaryInfoCaptor.capture(), groupNameCaptor.capture());
        assertThat(groupNameCaptor.getValue(), is(stepsovcCase.getFacility_code()));

        assertCaseOwnershipInfo(beneficiary, caregiver, beneficiaryInfoCaptor);
    }

    private void assertCaseOwnershipInfo(Beneficiary beneficiary, Caregiver caregiver, ArgumentCaptor<CaseOwnershipInformation> beneficiaryInfoCaptor) {
        assertThat(beneficiaryInfoCaptor.getValue().getUserName(), is(caregiver.getFirstName()));
        assertThat(beneficiaryInfoCaptor.getValue().getUserId(), is(caregiver.getCgId()));
        assertThat(beneficiaryInfoCaptor.getValue().getId(), is(beneficiary.getCaseId()));
        assertThat(beneficiaryInfoCaptor.getValue().getOwnerId(), is(caregiver.getCgId()));
    }


    private void assertBeneficiary(Beneficiary actual, Beneficiary expected) {

        assertThat(actual.getCaregiverCode(), is(expected.getCaregiverCode()));
        assertThat(actual.getCaseId(), is(expected.getCaseId()));
        assertThat(actual.getCode(), is(expected.getCode()));
        assertThat(actual.getDateOfBirth(), is(expected.getDateOfBirth()));
        assertThat(actual.getName(), is(expected.getName()));
        assertThat(actual.getSex(), is(expected.getSex()));
        assertThat(actual.getTitle(), is(expected.getTitle()));
    }
}
