package org.wv.stepsovc.commcare.gateway;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.motechproject.http.client.listener.HttpClientEventListener;
import org.motechproject.http.client.service.HttpClientService;
import org.wv.stepsovc.commcare.domain.CaseType;
import org.wv.stepsovc.commcare.repository.AllGroups;
import org.wv.stepsovc.commcare.repository.AllUsers;
import org.wv.stepsovc.vo.BeneficiaryInformation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommcareGatewayTest {

    CommcareGateway commcareGateway;

    @Mock
    HttpClientService mockHttpClientService;

    @Mock
    HttpClientEventListener mockHttpClientEventListener;

    @Mock
    VelocityEngine mockVelocityEngine;

    @Mock
    AllGroups allGroups;

    @Mock
    private AllUsers allUsers;


    @Before
    public void setup() {
        initMocks(this);
    }


    @Test
    public void shouldSubmitFormWithoutAnyException() throws Exception {
        CommcareGateway spyCommcareGateway = spy(new CommcareGateway(mockHttpClientService, mockVelocityEngine, mockHttpClientEventListener, allGroups, allUsers));
        BeneficiaryInformation beneficiaryInformation = getBeneficiaryInformation("f98589102c60fcc2e0f3c422bb361ebd", "cg1", UUID.randomUUID().toString(), "Albie-case", "ABC", "cg1", "null");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(CaseType.BENEFICIARY_CASE.getType(), beneficiaryInformation);
        doReturn(getExpectedXml()).when(spyCommcareGateway).getXmlFromObject(anyString(), eq(model));
        String url = "someurl";
        spyCommcareGateway.createNewBeneficiary(url, beneficiaryInformation);
        verify(spyCommcareGateway).getXmlFromObject(anyString(), eq(model));
        verify(mockHttpClientService).post(url, getExpectedXml());
    }

    @Test
    public void shouldSubmitUpdateOwnerForm() {
        CommcareGateway spyCommcareGateway = spy(new CommcareGateway(mockHttpClientService, mockVelocityEngine, mockHttpClientEventListener, allGroups, allUsers));
        BeneficiaryInformation beneficiaryInformation = getBeneficiaryInformation("f98589102c60fcc2e0f3c422bb361ebd", "cg1", UUID.randomUUID().toString(), "Albie-case", "ABC", "cg1", "null");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(CaseType.BENEFICIARY_CASE.getType(), beneficiaryInformation);
        doReturn(getExpectedXml()).when(spyCommcareGateway).getXmlFromObject(anyString(), eq(model));
        String url = "someurl";
        spyCommcareGateway.updateReferralOwner(url, beneficiaryInformation);
        verify(spyCommcareGateway).getXmlFromObject(anyString(), eq(model));
        verify(mockHttpClientService).post(url, getExpectedXml());
    }


    private BeneficiaryInformation getBeneficiaryInformation(String caregiverId, String caregiverCode, String caseId, String caseName, String beneficiaryCode, String caregiverName, String ownerId) {

        BeneficiaryInformation beneficiaryInformation = new BeneficiaryInformation();
        beneficiaryInformation.setBeneficiaryId(caseId);
        beneficiaryInformation.setBeneficiaryCode(beneficiaryCode);
        beneficiaryInformation.setBeneficiaryName(caseName);
        beneficiaryInformation.setBeneficiaryDob("12-12-1988");
        beneficiaryInformation.setBeneficiarySex("male");
        beneficiaryInformation.setBeneficiaryTitle("MR");
        beneficiaryInformation.setReceivingOrganization("XAQ");
        beneficiaryInformation.setCareGiverCode(caregiverCode);
        beneficiaryInformation.setCareGiverId(caregiverId);
        beneficiaryInformation.setCareGiverName(caregiverName);
        beneficiaryInformation.setCaseType(CaseType.BENEFICIARY_CASE.getType());
        beneficiaryInformation.setDateModified("2012-05-02T22:18:45.071+05:30");
        beneficiaryInformation.setOwnerId(ownerId);
        return beneficiaryInformation;
    }

    private String getExpectedXml() {

        return "<?xml version=\"1.0\"?>\n" +
                "<data xmlns=\"http://openrosa.org/formdesigner/A6E4F029-A971-41F1-80C1-9DDD5CC24571\" uiVersion=\"1\" version=\"12\"\n" +
                "      name=\"Registration\">\n" +
                "    <beneficiary_information>\n" +
                "        <beneficiary_name>Albie</beneficiary_name>\n" +
                "        <beneficiary_code>XYZ</beneficiary_code>\n" +
                "        <beneficiary_dob>12-12-1988</beneficiary_dob>\n" +
                "        <receiving_organization>XAQ</receiving_organization>\n" +
                "        <sex>male</sex>\n" +
                "        <title>MR</title>\n" +
                "    </beneficiary_information>\n" +
                "    <caregiver_information>\n" +
                "        <caregiver_id>f98589102c60fcc2e0f3c422bb361ebd</caregiver_id>\n" +
                "        <caregiver_name>cg1</caregiver_name>\n" +
                "    </caregiver_information>\n" +
                "    <form_type>beneficiary_registration</form_type>\n" +
                "    <case>\n" +
                "        <case_id>c7264b49-4e3d-4659-8df3-7316539829cb</case_id>\n" +
                "        <date_modified>2012-05-02T22:18:45.071+05:30</date_modified>\n" +
                "        <create>\n" +
                "            <case_type_id>Beneficiary</case_type_id>\n" +
                "            <case_name>test-case</case_name>\n" +
                "            <user_id>f98589102c60fcc2e0f3c422bb361ebd</user_id>\n" +
                "            <external_id>f98589102c60fcc2e0f3c422bb361ebd</external_id>\n" +
                "        </create>\n" +
                "        <update>\n" +
                "            <beneficiary_dob>12-12-1988</beneficiary_dob>\n" +
                "            <title>MR</title>\n" +
                "            <beneficiary_name>Albie</beneficiary_name>\n" +
                "            <receiving_organization>XAQ</receiving_organization>\n" +
                "            <caregiver_name>cg1</caregiver_name>\n" +
                "            <sex>male</sex>\n" +
                "            <form_type>beneficiary_registration</form_type>\n" +
                "            <beneficiary_code>XYZ</beneficiary_code>\n" +
                "            <caregiver_id>f98589102c60fcc2e0f3c422bb361ebd</caregiver_id>\n" +
                "        </update>\n" +
                "    </case>\n" +
                "    <meta>\n" +
                "        <deviceID>sadsa</deviceID>\n" +
                "        <timeStart>2012-05-02T22:18:45.071+05:30</timeStart>\n" +
                "        <timeEnd>2012-05-02T22:18:45.071+05:30</timeEnd>\n" +
                "        <username>cg1</username>\n" +
                "        <userID>f98589102c60fcc2e0f3c422bb361ebd</userID>\n" +
                "    </meta>\n" +
                "</data>";
    }
}
