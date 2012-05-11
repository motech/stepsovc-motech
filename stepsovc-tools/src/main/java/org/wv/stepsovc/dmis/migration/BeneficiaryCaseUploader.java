package org.wv.stepsovc.dmis.migration;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.dmis.DMISDataProcessor;
import org.wv.stepsovc.vo.*;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Component
public class BeneficiaryCaseUploader {


    @Autowired
    CommcareGateway commcareGateway;
    @Autowired
    DMISDataProcessor dmisDataProcessor;


    private static final String LOCATION = "/beneficiary.csv";
    private static final String COMMCARE_ENDPOINT = "http://127.0.0.1:7000/a/stepsovc/receiver";


    //0ben_code,1ben_first_name,2ben_middle_name,3ben_last_name,4ben_dob,5ben_gender,6bnt_id,cg_id

    public void readFromCsvAndSubmitBeneficiaryCaseToCommcare() throws Exception {
        final String path = getClass().getResource("/beneficiary.csv").getPath();
        List<String> beneficiaryRows = FileUtils.readLines(new File(path), "UTF-8");
        BeneficiaryFormRequest bfr = null;
        for (String row : beneficiaryRows) {
            final String[] arr = row.split(",");
            String id = "7ac0b33f0dac4a81c6d1fbf1bd9dfee0";
            bfr = getBeneficiaryFormRequest(dmisDataProcessor.decrypt(arr[1]), arr[4], arr[5], arr[0], id, "sdsds", "cg1");
            commcareGateway.createNewBeneficiary(COMMCARE_ENDPOINT, bfr);
        }

    }

    private BeneficiaryFormRequest getBeneficiaryFormRequest(String benName, String benDob, String benGender, String benCode, String careGiverId, String careGiverCode, String caregiveName) {

        BeneficiaryFormRequest beneficiaryFormRequest = new BeneficiaryFormRequest();

        final BeneficiaryInformation beneficiaryInformation = new BeneficiaryInformation();
        beneficiaryInformation.setCode(benCode);
        beneficiaryInformation.setName(benName);
        beneficiaryInformation.setDob(benDob);
        beneficiaryInformation.setSex(benGender);


        final CareGiverInformation careGiverInformation = new CareGiverInformation();
        careGiverInformation.setCode(careGiverCode);
        careGiverInformation.setName(caregiveName);
        careGiverInformation.setCommcareUserId(careGiverId);

        final CaseInformation caseInformation = new CaseInformation();
        caseInformation.setCaseTypeId("beneficiary");
        caseInformation.setId(UUID.randomUUID().toString());
        caseInformation.setDateModified(new DateTime().toString());
        caseInformation.setUserId(careGiverId);


        final MetaInformation metaInformation = new MetaInformation();
        metaInformation.setTimeStart(new DateTime().toString());
        metaInformation.setTimeEnd(new DateTime().toString());

        beneficiaryFormRequest.setBeneficiaryInformation(beneficiaryInformation);
        beneficiaryFormRequest.setCaregiverInformation(careGiverInformation);
        beneficiaryFormRequest.setCaseInformation(caseInformation);
        beneficiaryFormRequest.setMetaInformation(metaInformation);

        return beneficiaryFormRequest;
    }
}
