package org.wv.stepsovc.tools.exporter;

import org.motechproject.export.annotation.CSVDataSource;
import org.motechproject.export.annotation.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.services.BeneficiaryService;
import org.wv.stepsovc.core.services.ReferralService;
import org.wv.stepsovc.tools.domain.ReferralData;
import org.wv.stepsovc.tools.mapper.ReferralDataMapper;

import java.util.ArrayList;
import java.util.List;

@Component
@CSVDataSource(name = "referrals")
public class ReferralExporter {

    @Autowired
    private ReferralService referralService;
    @Autowired
    private ReferralDataMapper referralDataMapper;
    @Autowired
    private BeneficiaryService beneficiaryService;

    @DataProvider
    public List<ReferralData> referralReports() {
        List<Referral> referrals = referralService.getReferralDataForExport();
        List<ReferralData> referralDataList = new ArrayList<ReferralData>();
        for (Referral referral : referrals) {
            if(beneficiaryService.beneficiaryExists(referral.getBeneficiaryCode())) {
                ReferralData referralData = referralDataMapper.map(referral);
                referralData.setBeneficiaryId(beneficiaryService.getBeneficiaryId(referral.getBeneficiaryCode()));
                referralDataList.add(referralData);
            }
        }
        return referralDataList;
    }
}
