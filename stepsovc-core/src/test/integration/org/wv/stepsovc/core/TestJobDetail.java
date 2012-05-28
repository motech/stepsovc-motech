package org.wv.stepsovc.core;

import org.quartz.JobDetail;
import org.quartz.Trigger;

public class TestJobDetail {

    org.quartz.Trigger trigger;
    JobDetail jobDetail;

    public TestJobDetail(Trigger trigger, JobDetail jobDetail) {
        this.trigger = trigger;
        this.jobDetail = jobDetail;
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public org.quartz.Trigger trigger() {
        return trigger;
    }
}
