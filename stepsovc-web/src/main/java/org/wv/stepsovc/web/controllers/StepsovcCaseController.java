package org.wv.stepsovc.web.controllers;

import org.apache.log4j.Logger;
import org.motechproject.casexml.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wv.stepsovc.web.handlers.BeneficiaryRegistrationHandler;
import org.wv.stepsovc.web.handlers.NewReferralHandler;
import org.wv.stepsovc.web.handlers.UpdateReferralHandler;
import org.wv.stepsovc.web.handlers.UpdateServiceHandler;
import org.wv.stepsovc.web.request.BeneficiaryCase;
import org.wv.stepsovc.web.request.CaseUpdateType;

@Controller
@RequestMapping("/case/**")
public class StepsovcCaseController extends CaseService<BeneficiaryCase> {

    private static Logger logger = Logger.getLogger(StepsovcCaseController.class);

    private BeneficiaryRegistrationHandler beneficiaryRegistrationHandler;
    private NewReferralHandler newReferralHandler;
    private UpdateReferralHandler updateReferralHandler;
    private UpdateServiceHandler updateServiceHandler;

    public StepsovcCaseController(){
        super(BeneficiaryCase.class);
    }

    @Autowired
    public StepsovcCaseController(BeneficiaryRegistrationHandler beneficiaryRegistrationHandler,
                                  NewReferralHandler newReferralHandler, UpdateReferralHandler updateReferralHandler,
                                  UpdateServiceHandler updateServiceHandler) {
        super(BeneficiaryCase.class);
        this.beneficiaryRegistrationHandler = beneficiaryRegistrationHandler;
        this.newReferralHandler = newReferralHandler;
        this.updateReferralHandler = updateReferralHandler;
        this.updateServiceHandler = updateServiceHandler;
    }


    @Override
    public void closeCase(BeneficiaryCase beneficiaryCase) {
        logger.info("Inside close case");
    }

    @Override
    public void updateCase(BeneficiaryCase beneficiaryCase) {
        logger.info("Inside update case");
    }

    @Override
    public void createCase(BeneficiaryCase beneficiaryCase) {
        logger.info("Inside create case");

        if(CaseUpdateType.BENEFICIARY_REGISTRATION.getType().equals(beneficiaryCase.getForm_type()))
            beneficiaryRegistrationHandler.handleCase(beneficiaryCase);
        else if(CaseUpdateType.NEW_REFERRAL.getType().equals(beneficiaryCase.getForm_type()))
            newReferralHandler.handleCase(beneficiaryCase);
        else if(CaseUpdateType.UPDATE_REFERRAL.getType().equals(beneficiaryCase.getForm_type()))
            updateReferralHandler.handleCase(beneficiaryCase);
        else if(CaseUpdateType.UPDATE_SERVICE.getType().equals(beneficiaryCase.getForm_type()))
            updateServiceHandler.handleCase(beneficiaryCase);
    }
}

