package org.wv.stepsovc.core.repository;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.Service;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.motechproject.util.DateUtil.today;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:testCoreApplicationContext.xml")
public class AllReferralsIT {

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
        allReferrals.add(new Referral().setOvcId(ovcId2).setActive(true));

        allReferrals.add(new Referral().setOvcId(inactiveOvcId).setServiceDate(serviceDate).setActive(false));

        Referral actualReferral = allReferrals.findActiveByOvcId(ovcId);
        assertThat(actualReferral.getServiceDate(), is(serviceDate));

        actualReferral = allReferrals.findActiveByOvcId(ovcId2);

        assertNull(allReferrals.findActiveByOvcId(inactiveOvcId));
    }

    @Test
    public void shouldReturnOnlyReferralsWhichAreToBeExported() {
        Referral referralToBeExported = new Referral();
        String ovcId1 = "ref123";
        referralToBeExported.setOvcId(ovcId1);
        referralToBeExported.setLastModified(today());
        allReferrals.add(referralToBeExported);

        Referral referral1 = new Referral();
        String ovcId2 = "ref123";
        referral1.setOvcId(ovcId2);
        referral1.setLastModified(today().minusDays(1));
        allReferrals.add(referral1);

        Referral referral2 = new Referral();
        referral2.setOvcId("ref456");
        referral2.setLastModified(today().minusDays(2));
        allReferrals.add(referral2);

        List<Referral> allToBeExported = allReferrals.getAllModifiedBetween(today().minusDays(1), today());
        assertEquals(2, allToBeExported.size());
        assertEquals(ovcId1, allToBeExported.get(0).getOvcId());
        assertEquals(ovcId2, allToBeExported.get(1).getOvcId());

    }

    @After
    public void cleanUp() {
        allReferrals.removeAll();
    }
}
