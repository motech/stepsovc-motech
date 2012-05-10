package org.wv.stepsovc.web.controllers;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.wv.stepsovc.web.request.BeneficiaryCaseUpdateType;
import org.wv.stepsovc.web.request.CaseType;
import org.wv.stepsovc.web.request.StepsovcCase;
import org.wv.stepsovc.web.services.BeneficiaryService;
import org.wv.stepsovc.web.services.ReferralService;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


public class StepsovcCaseControllerTest {

    @Mock
    BeneficiaryService mockBeneficiaryService;
    @Mock
    VelocityEngine velocityEngine;
    @Mock
    ReferralService mockReferralService;

    StepsovcCaseController stepsovcCaseController;
    private StepsovcCase stepsovcCase;


    @Before
    public void setup() {
        initMocks(this);
        stepsovcCaseController = new StepsovcCaseController(mockBeneficiaryService, mockReferralService);
        stepsovcCase = new StepsovcCase();
    }


    @Test
    public void ShouldCallBeneficiaryRegistrationHandler() throws Exception {
        stepsovcCase.setCase_type(CaseType.BENEFICIARY_CASE.getType());
        stepsovcCase.setForm_type(BeneficiaryCaseUpdateType.BENEFICIARY_REGISTRATION.getType());
        stepsovcCaseController.createCase(stepsovcCase);
        verify(mockBeneficiaryService).createBeneficiary(stepsovcCase);
    }

    @Test
    public void ShouldCallNewReferralHandler() throws Exception {
        stepsovcCase.setCase_type(CaseType.BENEFICIARY_CASE.getType());
        stepsovcCase.setForm_type(BeneficiaryCaseUpdateType.NEW_REFERRAL.getType());
        stepsovcCaseController.createCase(stepsovcCase);
        verify(mockReferralService).addNewReferral(stepsovcCase);
    }

    @Test
    public void ShouldCallUpdateReferralHandler() throws Exception {
        stepsovcCase.setCase_type(CaseType.BENEFICIARY_CASE.getType());
        stepsovcCase.setForm_type(BeneficiaryCaseUpdateType.UPDATE_REFERRAL.getType());
        stepsovcCaseController.createCase(stepsovcCase);
        verify(mockReferralService).updateNotAvailedReasons(stepsovcCase);
    }

    @Test
    public void ShouldCallUpdateServiceHandler() throws Exception {
        stepsovcCase.setCase_type(CaseType.BENEFICIARY_CASE.getType());
        stepsovcCase.setForm_type(BeneficiaryCaseUpdateType.UPDATE_SERVICE.getType());
        stepsovcCaseController.createCase(stepsovcCase);
        verify(mockReferralService).updateAvailedServices(stepsovcCase);
    }
}
