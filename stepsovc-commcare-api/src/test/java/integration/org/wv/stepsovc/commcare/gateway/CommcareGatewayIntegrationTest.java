package org.wv.stepsovc.commcare.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.commcare.domain.CaseType;
import org.wv.stepsovc.vo.BeneficiaryInformation;

import java.util.HashMap;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-stepsovc-commcare-api.xml")
public class CommcareGatewayIntegrationTest {

    @Autowired
    CommcareGateway commcareGateway;


    @Test
    public void shouldCreateNewBeneficiary() throws Exception {
        BeneficiaryInformation beneficiaryInformation = getBeneficiaryInformation("7ac0b33f0dac4a81c6d1fbf1bd9dfee0", "cg1", UUID.randomUUID().toString(), "new-test-case-5", "new-test-case-5", "cg1", null);
        String url = "http://127.0.0.1:7000/a/stepsovc/receiver";
        commcareGateway.createNewBeneficiary(url, beneficiaryInformation);
    }

    @Test
    public void testObjectToXmlConversion() {
        BeneficiaryInformation beneficiaryInformation = getBeneficiaryInformation("f98589102c60fcc2e0f3c422bb361ebd", "cg1", "c7264b49-4e3d-4659-8df3-7316539829cb", "test-case", "XYZ/123", "cg1", "hw1");
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put(CaseType.BENEFICIARY_CASE.getType(), beneficiaryInformation);

        String actual = commcareGateway.getXmlFromObject("/templates/beneficiary-case-form.xml", model);
        assertEquals(getExpectedBeneficiaryCaseXml(), actual);

        actual = commcareGateway.getXmlFromObject("/templates/update-owner-form.xml", model);
        assertEquals(getExpectedUpdateOwnerXml(), actual);
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

    private String getExpectedBeneficiaryCaseXml() {

        return "<?xml version=\"1.0\"?>\n" +
                "<data xmlns=\"http://openrosa.org/formdesigner/A6E4F029-A971-41F1-80C1-9DDD5CC24571\" uiVersion=\"1\" version=\"12\"\n" +
                "      name=\"Registration\">\n" +
                "    <beneficiary_information>\n" +
                "        <beneficiary_name>test-case</beneficiary_name>\n" +
                "        <beneficiary_code>XYZ/123</beneficiary_code>\n" +
                "        <beneficiary_dob>12-12-1988</beneficiary_dob>\n" +
                "        <receiving_organization>XAQ</receiving_organization>\n" +
                "        <sex>male</sex>\n" +
                "        <title>MR</title>\n" +
                "    </beneficiary_information>\n" +
                "    <caregiver_information>\n" +
                "        <caregiver_code>cg1</caregiver_code>\n" +
                "        <caregiver_name>cg1</caregiver_name>\n" +
                "    </caregiver_information>\n" +
                "    <form_type>beneficiary_registration</form_type>\n" +
                "    <case>\n" +
                "        <case_id>c7264b49-4e3d-4659-8df3-7316539829cb</case_id>\n" +
                "        <date_modified>2012-05-02T22:18:45.071+05:30</date_modified>\n" +
                "        <create>\n" +
                "            <case_type_id>beneficiary</case_type_id>\n" +
                "            <case_name>XYZ/123</case_name>\n" +
                "            <user_id>f98589102c60fcc2e0f3c422bb361ebd</user_id>\n" +
                "            <external_id>XYZ/123</external_id>\n" +
                "        </create>\n" +
                "        <update>\n" +
                "            <beneficiary_dob>12-12-1988</beneficiary_dob>\n" +
                "            <title>MR</title>\n" +
                "            <beneficiary_name>test-case</beneficiary_name>\n" +
                "            <receiving_organization>XAQ</receiving_organization>\n" +
                "            <caregiver_name>cg1</caregiver_name>\n" +
                "            <sex>male</sex>\n" +
                "            <form_type>beneficiary_registration</form_type>\n" +
                "            <beneficiary_code>XYZ/123</beneficiary_code>\n" +
                "            <caregiver_code>cg1</caregiver_code>\n" +
                "        </update>\n" +
                "    </case>\n" +
                "    <meta>\n" +
                "        <username>cg1</username>\n" +
                "        <userID>f98589102c60fcc2e0f3c422bb361ebd</userID>\n" +
                "    </meta>\n" +
                "</data>";
    }

    private String getExpectedUpdateOwnerXml() {
        return "<?xml version='1.0' ?>\n" +
                "<data uiVersion=\"1\" version=\"89\" name=\"update\"\n" +
                "      xmlns=\"http://openrosa.org/formdesigner/E45F3EFD-A7BE-42A6-97C2-5133E766A2AA\">\n" +
                "    <owner_id>hw1</owner_id>\n" +
                "    <form_type>custom_update</form_type>\n" +
                "    <case>\n" +
                "        <case_id>c7264b49-4e3d-4659-8df3-7316539829cb</case_id>\n" +
                "        <date_modified>2012-05-02T22:18:45.071+05:30</date_modified>\n" +
                "        <update>\n" +
                "            <form_type>custom_update</form_type>\n" +
                "            <owner_id>hw1</owner_id>\n" +
                "        </update>\n" +
                "    </case>\n" +
                "    <meta>\n" +
                "        <username>cg1</username>\n" +
                "        <userID>f98589102c60fcc2e0f3c422bb361ebd</userID>\n" +
                "    </meta>\n" +
                "</data>";
    }

}