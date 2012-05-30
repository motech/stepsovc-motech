package org.wv.stepsovc.tools.seed.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wv.stepsovc.core.domain.Beneficiary;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.tools.seed.Seed;

@Component
public class BeneficiaryTemporarySeed extends Seed {

    @Autowired
    private AllBeneficiaries allBeneficiaries;

    @Override
    protected void preLoad() {
        super.preLoad();
    }

    @Override
    protected void load() {
        allBeneficiaries.removeAll();
        allBeneficiaries.add(new Beneficiary("Beneficiary1", "BEN001", "CG1", "3D6OA6XQJWS5M0MWVNB97C9QC"));
        allBeneficiaries.add(new Beneficiary("Beneficiary2","BEN2","CG1","TTEZ0JHRBW74AK00KI2PUV9UZ"));
    }
}
