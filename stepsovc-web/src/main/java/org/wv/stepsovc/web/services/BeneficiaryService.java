package org.wv.stepsovc.web.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.wv.stepsovc.web.mapper.BeneficiaryMapper;
import org.wv.stepsovc.web.repository.AllBeneficiaries;
import org.wv.stepsovc.web.request.StepsovcCase;

public class BeneficiaryService {

    @Autowired
    AllBeneficiaries allBeneficiaries;

    private static Logger logger = Logger.getLogger(BeneficiaryService.class);

    public void createBeneficiary(StepsovcCase stepsovcCase) {
        logger.info("Handling beneficiary registration for "+ stepsovcCase.getBeneficiary_name());

        if(allBeneficiaries.findBeneficiary(stepsovcCase.getBeneficiary_code()) == null)
            allBeneficiaries.add(new BeneficiaryMapper().map(stepsovcCase));
        else
            logger.error("Beneficiary already present in database, "+ stepsovcCase.getBeneficiary_code());
    }
}
