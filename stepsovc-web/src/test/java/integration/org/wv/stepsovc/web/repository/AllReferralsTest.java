package org.wv.stepsovc.web.repository;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.web.domain.Referral;
import org.wv.stepsovc.web.domain.Service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:testApplicationContext.xml")
public class AllReferralsTest {

    @Autowired
    AllReferrals allReferrals;

    @Test
    public void shouldFindReferralByOvcId() {
        String ovcId = "4589076";
        String ovcId2 = "4589077";
        String inactiveOvcId = "124123131241";
        String serviceDate = DateUtil.now().toString("dd-MMM-yyyy hh:mm:ss");

        allReferrals.add(new Referral().setOvcId(ovcId).setServiceDate(serviceDate).setActive(true));
        Service familyPlanning = new Service().setReason("family gninnalp");
        allReferrals.add(new Referral().setOvcId(ovcId2).setFamilyPlanning(familyPlanning).setActive(true));

        allReferrals.add(new Referral().setOvcId(inactiveOvcId).setServiceDate(serviceDate).setActive(false));

        Referral actualReferral = allReferrals.findActiveByOvcId(ovcId);
        assertThat(actualReferral.getServiceDate(), is(serviceDate));

        actualReferral = allReferrals.findActiveByOvcId(ovcId2);
        assertThat(actualReferral.getFamilyPlanning().getReason(), is(familyPlanning.getReason()));

        assertNull(allReferrals.findActiveByOvcId(inactiveOvcId));
    }

    @After
    public void cleanUp() {
        allReferrals.removeAll();
    }
}
