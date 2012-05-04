package org.wv.stepsovc.web.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.wv.stepsovc.web.mapper.BeneficiaryMapper;
import org.wv.stepsovc.web.repository.AllBeneficiaries;
import org.wv.stepsovc.web.request.BeneficiaryCase;

public class BeneficiaryService {

    @Autowired
    AllBeneficiaries allBeneficiaries;

    private static Logger logger = Logger.getLogger(BeneficiaryService.class);

    public void createBeneficiary(BeneficiaryCase beneficiaryCase) {
        logger.info("Handling beneficiary registration for "+beneficiaryCase.getBeneficiary_name());

        if(allBeneficiaries.findBeneficiary(beneficiaryCase.getBeneficiary_code()) == null)
            allBeneficiaries.add(new BeneficiaryMapper().map(beneficiaryCase));
        else
            logger.error("Beneficiary already present in database, "+beneficiaryCase.getBeneficiary_code());
    }
}
