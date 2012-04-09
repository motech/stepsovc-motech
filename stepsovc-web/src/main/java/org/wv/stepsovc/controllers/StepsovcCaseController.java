package org.wv.stepsovc.controllers;

import org.motechproject.commcare.parser.CommcareCaseParser;
import org.motechproject.commcare.service.CaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wv.stepsovc.request.BeneficiaryCase;

/**
 * Created by IntelliJ IDEA.
 * User: balajig
 * Date: 4/8/12
 * Time: 10:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/case/**")
public class StepsovcCaseController extends CaseService<BeneficiaryCase> {

    CommcareCaseParser<BeneficiaryCase> caseParser;

    public StepsovcCaseController() {
        super(BeneficiaryCase.class);
    }


    @Override
    public void closeCase(BeneficiaryCase beneficiaryCase) {
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println("INside  close case");
    }

    @Override
    public void updateCase(BeneficiaryCase beneficiaryCase) {
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println("INside  update case");
    }

    @Override
    public void createCase(BeneficiaryCase beneficiaryCase) {
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println("INside  create case");
    }
}
