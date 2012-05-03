package org.wv.stepsovc.web.controllers;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.wv.stepsovc.web.handlers.BeneficiaryRegistrationHandler;
import org.wv.stepsovc.web.handlers.NewReferralHandler;
import org.wv.stepsovc.web.handlers.UpdateReferralHandler;
import org.wv.stepsovc.web.handlers.UpdateServiceHandler;
import org.wv.stepsovc.web.request.BeneficiaryCase;
import org.wv.stepsovc.web.request.CaseUpdateType;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


public class StepsovcCaseControllerTest {

    @Mock
    BeneficiaryRegistrationHandler mockBeneficiaryHandler;
    @Mock
    NewReferralHandler mockNewReferralHandler;
    @Mock
    UpdateReferralHandler mockUpdateReferralHandler;
    @Mock
    UpdateServiceHandler mockUpdateServiceHandler;
    @Mock
    VelocityEngine velocityEngine;


    StepsovcCaseController stepsovcCaseController;
    private BeneficiaryCase beneficiaryCase;


    @Before
    public void setup() {
        initMocks(this);
        stepsovcCaseController = new StepsovcCaseController(mockBeneficiaryHandler, mockNewReferralHandler, mockUpdateReferralHandler, mockUpdateServiceHandler);
        beneficiaryCase = new BeneficiaryCase();
    }


    @Test
    public void ShouldCallBeneficiaryRegistrationHandler() throws Exception {
        beneficiaryCase.setForm_type(CaseUpdateType.BENEFICIARY_REGISTRATION.getType());
        stepsovcCaseController.createCase(beneficiaryCase);
        verify(mockBeneficiaryHandler).handleCase(beneficiaryCase);
    }

    @Test
    public void ShouldCallNewReferralHandler() throws Exception {
        beneficiaryCase.setForm_type(CaseUpdateType.NEW_REFERRAL.getType());
        stepsovcCaseController.createCase(beneficiaryCase);
        verify(mockNewReferralHandler).handleCase(beneficiaryCase);
    }

    @Test
    public void ShouldCallUpdateReferralHandler() throws Exception {
        beneficiaryCase.setForm_type(CaseUpdateType.UPDATE_REFERRAL.getType());
        stepsovcCaseController.createCase(beneficiaryCase);
        verify(mockUpdateReferralHandler).handleCase(beneficiaryCase);
    }

    @Test
    public void ShouldCallUpdateServiceHandler() throws Exception {
        beneficiaryCase.setForm_type(CaseUpdateType.UPDATE_SERVICE.getType());
        stepsovcCaseController.createCase(beneficiaryCase);
        verify(mockUpdateServiceHandler).handleCase(beneficiaryCase);
    }
}
