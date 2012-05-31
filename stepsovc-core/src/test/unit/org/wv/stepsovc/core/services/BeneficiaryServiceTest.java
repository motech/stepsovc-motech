package org.wv.stepsovc.core.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.commcare.vo.BeneficiaryInformation;
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
    private AllCaregivers allCaregivers;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        beneficiaryService = new BeneficiaryService();
        ReflectionTestUtils.setField(beneficiaryService, "allBeneficiaries", allBeneficiaries);
        ReflectionTestUtils.setField(beneficiaryService, "commcareGateway", commcareGateway);
        ReflectionTestUtils.setField(beneficiaryService, "mockAllCaregivers", allCaregivers);
    }

    @Test
    public void shouldCreateBeneficiaryIfNotExists() throws Exception {
        String benCode = "BEN001";
        StepsovcCase stepsOvcCase = StepsovcCaseFixture.createNewCase(benCode);
        beneficiaryService.createBeneficiary(stepsOvcCase);

        ArgumentCaptor<Beneficiary> beneficiaryCaptor = ArgumentCaptor.forClass(Beneficiary.class);

        doReturn(null).when(allBeneficiaries).findBeneficiary(benCode);
        verify(allBeneficiaries).add(beneficiaryCaptor.capture());
        assertBeneficiary(beneficiaryCaptor.getValue(), new BeneficiaryMapper().map(stepsOvcCase));

    }

    @Test
    public void shouldRemoveBeneficiaryIfExistsAndCreateNewOne() throws Exception {
        String benCode = "BEN001";
        StepsovcCase stepsOvcCase = StepsovcCaseFixture.createNewCase(benCode);
        Beneficiary beneficiary = new Beneficiary();
        doReturn(beneficiary).when(allBeneficiaries).findBeneficiary(benCode);
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

        doReturn(null).when(allBeneficiaries).findBeneficiary(benCode);
        doReturn(new Caregiver()).when(allCaregivers).findCaregiverByCode(stepsovcCase.getCaregiver_code());

        beneficiaryService.addUserOwnership(stepsovcCase);

        verifyZeroInteractions(commcareGateway);

    }

    @Test
    public void shouldNotAddUserOwnershipIfCaregiverNotExists() throws Exception {
        String benCode = "BEN001";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createNewCase(benCode);

        doReturn(new Beneficiary()).when(allBeneficiaries).findBeneficiary(benCode);
        doReturn(null).when(allCaregivers).findCaregiverByCode(stepsovcCase.getCaregiver_code());

        beneficiaryService.addUserOwnership(stepsovcCase);

        verifyZeroInteractions(commcareGateway);

    }

    @Test
    public void shouldAddOwnershipIfBothCaregiverAndBeneficiaryExists() throws Exception {
        String benCode = "BEN001";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createNewCase(benCode);
        Caregiver requestingCaregiver = new Caregiver();
        Beneficiary beneficiary = new Beneficiary();
        String caregiverId = "caregiverId";
        String caregiverCode = "caregiverCode";

        beneficiary.setCaregiverCode("CG1");
        beneficiary.setCaseId("SomecaseId");
        Caregiver caregiver = new Caregiver();
        caregiver.setFirstName("caregiver1");
        caregiver.setCgId("someCaregiverID");
        requestingCaregiver.setCgId(caregiverId);
        beneficiary.setCaregiverCode(caregiverCode);

        doReturn(beneficiary).when(allBeneficiaries).findBeneficiary(benCode);
        doReturn(requestingCaregiver).when(allCaregivers).findCaregiverByCode(stepsovcCase.getCaregiver_code());
        doReturn(caregiver).when(allCaregivers).findCaregiverByCode(caregiverCode);

        beneficiaryService.addUserOwnership(stepsovcCase);

        ArgumentCaptor<BeneficiaryInformation> beneficiaryInfoCaptor = ArgumentCaptor.forClass(BeneficiaryInformation.class);
        ArgumentCaptor<String> userIdCaptor = ArgumentCaptor.forClass(String.class);

        verify(commcareGateway).addUserOwnership(beneficiaryInfoCaptor.capture(), userIdCaptor.capture());

        assertThat(userIdCaptor.getValue(), is(caregiverId));
        assertBeneficiaryInfo(beneficiary, caregiver, beneficiaryInfoCaptor);
    }

    @Test
    public void shouldNotAddGroupOwnershipIfBeneficiaryDoesNotExists() throws Exception {
        String benCode = "BenCode";
        StepsovcCase stepsovcCase = StepsovcCaseFixture.createNewCase(benCode);

        doReturn(null).when(allBeneficiaries).findBeneficiary(benCode);

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
        doReturn(beneficiary).when(allBeneficiaries).findBeneficiary(bencode);
        doReturn(caregiver).when(allCaregivers).findCaregiverByCode(caregiverCode);
        beneficiaryService.addGroupOwnership(stepsovcCase);
        ArgumentCaptor<BeneficiaryInformation> beneficiaryInfoCaptor = ArgumentCaptor.forClass(BeneficiaryInformation.class);
        ArgumentCaptor<String> groupNameCaptor = ArgumentCaptor.forClass(String.class);
        verify(commcareGateway).addGroupOwnership(beneficiaryInfoCaptor.capture(), groupNameCaptor.capture());
        assertThat(groupNameCaptor.getValue(), is(stepsovcCase.getFacility_code()));

        assertBeneficiaryInfo(beneficiary, caregiver, beneficiaryInfoCaptor);
    }

    private void assertBeneficiaryInfo(Beneficiary beneficiary, Caregiver caregiver, ArgumentCaptor<BeneficiaryInformation> beneficiaryInfoCaptor) {
        assertThat(beneficiaryInfoCaptor.getValue().getCareGiverName(), is(caregiver.getFirstName()));
        assertThat(beneficiaryInfoCaptor.getValue().getCareGiverCode(), is(beneficiary.getCaregiverCode()));
        assertThat(beneficiaryInfoCaptor.getValue().getCareGiverId(), is(caregiver.getCgId()));
        assertThat(beneficiaryInfoCaptor.getValue().getBeneficiaryId(), is(beneficiary.getCaseId()));
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
