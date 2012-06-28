package org.wv.stepsovc.core.services;

import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wv.stepsovc.core.configuration.ScheduleNames;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.mapper.ScheduleEnrollmentMapper;

import java.util.Arrays;

@Service
public class StepsovcScheduleService {

    @Autowired
    private ScheduleTrackingService scheduleTrackingService;
    @Autowired
    private StepsovcAlertService stepsovcAlertService;

    public void scheduleNewReferral(Referral referral) {
        scheduleTrackingService.enroll(new ScheduleEnrollmentMapper().map(referral));
        stepsovcAlertService.sendInstantReferralAlertToFacility(referral);
    }

    public void unscheduleReferral(Referral referral) {
        scheduleTrackingService.unenroll(referral.getOvcId(), Arrays.asList(ScheduleNames.REFERRAL.getName()));
    }

}
