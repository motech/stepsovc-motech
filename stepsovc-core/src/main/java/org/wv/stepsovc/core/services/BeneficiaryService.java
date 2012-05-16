package org.wv.stepsovc.core.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wv.stepsovc.core.mapper.BeneficiaryMapper;
import org.wv.stepsovc.core.repository.AllBeneficiaries;
import org.wv.stepsovc.core.request.StepsovcCase;

@Service
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
