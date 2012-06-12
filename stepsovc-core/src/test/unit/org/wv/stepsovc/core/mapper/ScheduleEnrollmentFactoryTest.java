package org.wv.stepsovc.core.mapper;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.motechproject.scheduletracking.api.service.EnrollmentRequest;
import org.wv.stepsovc.core.configuration.ScheduleNames;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.factories.ScheduleEnrollmentFactory;
import org.wv.stepsovc.core.utils.DateUtils;

import static junit.framework.Assert.assertEquals;

public class ScheduleEnrollmentFactoryTest {

    private ScheduleEnrollmentFactory scheduleEnrollmentfactory;

    @Before
    public void setUp() throws Exception {
        scheduleEnrollmentfactory = new ScheduleEnrollmentFactory();
    }

    @Test
    public void shouldCreateReferralEnrollmentRequest() throws Exception {
        Referral referral = new Referral();
        String serviceDate = "2012-06-12";
        referral.setServiceDate(serviceDate);
        String ovcId = "ovcId";
        LocalDate scheduleReferenceDate = DateUtils.prevLocalDate(serviceDate);
        referral.setOvcId(ovcId);
        EnrollmentRequest enrollmentRequest = scheduleEnrollmentfactory.createEnrollmentRequest(referral, ScheduleNames.REFERRAL.getName());
        assertEquals(ovcId, enrollmentRequest.getExternalId());
        assertEquals(ScheduleNames.REFERRAL.getName(), enrollmentRequest.getScheduleName());
        assertEquals(scheduleReferenceDate, enrollmentRequest.getReferenceDate());
    }

    @Test
    public void shouldCreateDefaultEnrollmentRequest() throws Exception {
        Referral referral = new Referral();
        String serviceDate = "2012-06-12";
        referral.setServiceDate(serviceDate);
        String ovcId = "ovcId";
        LocalDate scheduleReferenceDate = DateUtils.getLocalDate(serviceDate);
        referral.setOvcId(ovcId);
        EnrollmentRequest enrollmentRequest = scheduleEnrollmentfactory.createEnrollmentRequest(referral, ScheduleNames.DEFAULTMENT.getName());
        assertEquals(ovcId, enrollmentRequest.getExternalId());
        assertEquals(ScheduleNames.DEFAULTMENT.getName(), enrollmentRequest.getScheduleName());
        assertEquals(scheduleReferenceDate, enrollmentRequest.getReferenceDate());
    }
}
