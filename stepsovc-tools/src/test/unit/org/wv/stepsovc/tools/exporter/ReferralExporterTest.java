package org.wv.stepsovc.tools.exporter;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.services.BeneficiaryService;
import org.wv.stepsovc.core.services.ReferralService;
import org.wv.stepsovc.tools.domain.ReferralData;
import org.wv.stepsovc.tools.mapper.ReferralDataMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class ReferralExporterTest {

    @Mock
    private ReferralService mockReferralService;
    @Mock
    private ReferralDataMapper mockReferralDataMapper;
    @Mock
    private BeneficiaryService mockBeneficiaryService;

    @Test
    public void shouldReturnReferralDataForExport() {
        initMocks(this);
        ReferralExporter referralExporter = new ReferralExporter();
        ReflectionTestUtils.setField(referralExporter, "referralService", mockReferralService);
        ReflectionTestUtils.setField(referralExporter, "referralDataMapper", mockReferralDataMapper);
        ReflectionTestUtils.setField(referralExporter, "beneficiaryService", mockBeneficiaryService);

        Object benId1 = "benId1";
        Object benId2 = "benId2";
        String benCode1 = "benCode1";
        String benCode2 = "benCode2";
        Referral referral1 = new Referral();
        referral1.setBeneficiaryCode(benCode1);
        Referral referral2 = new Referral();
        referral2.setBeneficiaryCode(benCode2);
        ReferralData referralData1 = new ReferralData();
        ReferralData referralData2 = new ReferralData();
        doReturn(Arrays.<Referral>asList(referral1, referral2)).when(mockReferralService).getReferralDataForExport();
        doReturn(referralData1).when(mockReferralDataMapper).map(referral1);
        doReturn(referralData2).when(mockReferralDataMapper).map(referral2);
        doReturn(benId1).when(mockBeneficiaryService).getBeneficiaryId(benCode1);
        doReturn(benId2).when(mockBeneficiaryService).getBeneficiaryId(benCode2);

        List<ReferralData> referralDataList = referralExporter.referralReports();
        assertTrue(referralDataList.contains(referralData1));
        assertTrue(referralDataList.contains(referralData2));
        assertEquals(benId1, referralData1.getBeneficiaryId());
        assertEquals(benId2, referralData2.getBeneficiaryId());
    }


}
