package org.wv.stepsovc.core.domain;

import org.junit.Test;
import org.wv.stepsovc.core.fixtures.StepsovcCaseFixture;
import org.wv.stepsovc.core.mapper.ReferralMapper;
import org.wv.stepsovc.core.request.StepsovcCase;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ReferralTest {

    @Test
    public void shouldReturnFullfillStatusOfReferral() throws Exception {

        StepsovcCase stepsovcCase = StepsovcCaseFixture.createCaseForReferral("someCode", "", "");
        Referral referral = new ReferralMapper().map(stepsovcCase);
        stepsovcCase = StepsovcCaseFixture.createCaseForUpdateService("someCode", "2012-06-12");
        referral = new ReferralMapper().updateServices(referral, stepsovcCase);
        assertFalse(referral.fullfilled());
        stepsovcCase = StepsovcCaseFixture.createCaseForUpdateServiceWithServicesFullfilled("someCode", "2012-06-12");
        referral = new ReferralMapper().updateServices(referral, stepsovcCase);
        assertTrue(referral.fullfilled());
    }
}
