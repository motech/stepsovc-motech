package org.wv.stepsovc.core.services;

import org.joda.time.LocalTime;
import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wv.stepsovc.core.configuration.ScheduleNames;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.factories.FollowUpVisitFactory;
import org.wv.stepsovc.core.mapper.ScheduleEnrollmentMapper;
import org.wv.stepsovc.core.repository.AllAppointments;
import org.wv.stepsovc.core.utils.DateUtils;

import java.util.Arrays;

@Service
public class StepsovcScheduleService {

    @Autowired
    private ScheduleTrackingService scheduleTrackingService;
    @Autowired
    private StepsovcAlertService stepsovcAlertService;
    @Autowired
    AllAppointments allAppointments;
    @Autowired
    private FollowUpVisitFactory followUpVisitFactory;

    private static final String FOLLOW_UP_REQUIRED = "yes";

    public void scheduleNewReferral(Referral referral) {
        scheduleTrackingService.enroll(new ScheduleEnrollmentMapper().map(referral));
        stepsovcAlertService.sendInstantReferralAlertToFacility(referral);

        if (FOLLOW_UP_REQUIRED.equals(referral.getFollowupRequired()))
            allAppointments.add(referral.getOvcId(), followUpVisitFactory.createFollowUpVisitRequest(referral.appointmentDataMap()
                    , DateUtils.getLocalDate(referral.getFollowupDate()).toDateTime(new LocalTime(0, 0))));
    }

    public void unscheduleReferral(Referral referral) {
        scheduleTrackingService.unenroll(referral.getOvcId(), Arrays.asList(ScheduleNames.REFERRAL.getName()));
    }

}
