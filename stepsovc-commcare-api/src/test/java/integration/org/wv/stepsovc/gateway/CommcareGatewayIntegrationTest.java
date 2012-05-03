package org.wv.stepsovc.gateway;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.vo.*;

import java.util.HashMap;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static org.wv.stepsovc.utils.ConstantUtils.BENEFICIARY;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-stepsovc-commcare-api.xml")
public class CommcareGatewayIntegrationTest {

    @Autowired
    CommcareGateway commcareGateway;


    @Ignore
    public void testSubmitForm() throws Exception {
        final BeneficiaryFormRequest beneficiaryFormRequest = getBeneficiaryFormRequest("f98589102c60fcc2e0f3c422bb361ebd", "cg1", UUID.randomUUID().toString(), "Albie-case");
        String url = "http://127.0.0.1:7000/a/stepsovc/receiver";
        commcareGateway.submitForm(url, beneficiaryFormRequest);
    }

    @Test
    public void testObjectToXmlConverion() {
        final BeneficiaryFormRequest beneficiaryFormRequest = getBeneficiaryFormRequest("f98589102c60fcc2e0f3c422bb361ebd", "cg1", "c7264b49-4e3d-4659-8df3-7316539829cb", "test-case");
        final HashMap<String, Object> model = new HashMap<String, Object>();
        model.put(BENEFICIARY, beneficiaryFormRequest);
        String actual = commcareGateway.getXmlFromObject("/templates/beneficiary-form.xml", model);
        assertEquals(getExpectedXml(), actual);
    }

    private BeneficiaryFormRequest getBeneficiaryFormRequest(String userId, String caregiveName, String caseId, String caseName) {

        BeneficiaryFormRequest beneficiaryFormRequest = new BeneficiaryFormRequest();

        final BeneficiaryInformation beneficiaryInformation = new BeneficiaryInformation();
        beneficiaryInformation.setCode("XYZ");
        beneficiaryInformation.setName("Albie");
        beneficiaryInformation.setDob("12-12-1988");
        beneficiaryInformation.setSex("male");
        beneficiaryInformation.setTitle("MR");
        beneficiaryInformation.setReceivingOrganization("XAQ");


        final CareGiverInformation careGiverInformation = new CareGiverInformation();
        careGiverInformation.setId(userId);
        careGiverInformation.setName(caregiveName);

        final CaseInformation caseInformation = new CaseInformation();
        caseInformation.setCaseTypeId("Beneficiary");
        caseInformation.setId(caseId);
        caseInformation.setDateModified("2012-05-02T22:18:45.071+05:30");
        caseInformation.setExternalId(userId);
        caseInformation.setUserId(userId);
        caseInformation.setName(caseName);


        final MetaInformation metaInformation = new MetaInformation();
        metaInformation.setDeviceId("sadsa");
        metaInformation.setTimeStart("2012-05-02T22:18:45.071+05:30");
        metaInformation.setTimeEnd("2012-05-02T22:18:45.071+05:30");

        beneficiaryFormRequest.setBeneficiaryInformation(beneficiaryInformation);
        beneficiaryFormRequest.setCaregiverInformation(careGiverInformation);
        beneficiaryFormRequest.setCaseInformation(caseInformation);
        beneficiaryFormRequest.setMetaInformation(metaInformation);

        return beneficiaryFormRequest;
    }

    private String getExpectedXml() {

        return "<?xml version=\"1.0\"?>\n" +
                "<data xmlns:jrm=\"http://dev.commcarehq.org/jr/xforms\" xmlns=\"http://openrosa.org/formdesigner/A6E4F029-A971-41F1-80C1-9DDD5CC24571\" uiVersion=\"1\" version=\"12\" name=\"Registration\">\n" +
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
