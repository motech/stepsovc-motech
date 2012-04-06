package org.wv.stepsovc.controllers;

import org.motechproject.commcare.service.CaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wv.stepsovc.request.BeneficiaryCase;

/**
 * Created by IntelliJ IDEA.
 * User: balajig
 * Date: 4/6/12
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping(value = "/case/*")
public class StepsOVCCaseController extends CaseService<BeneficiaryCase> {

    public StepsOVCCaseController() {
        super(BeneficiaryCase.class);
    }

    @Override
    public void closeCase(BeneficiaryCase beneficiaryCase) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateCase(BeneficiaryCase beneficiaryCase) {
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println("***** Request Received for Case updation *******");
    }

    @Override
    public void createCase(BeneficiaryCase beneficiaryCase) {
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println("***** Request Received for Case creation *******");
    }
}
