package integration.org.wv.stepsovc.web;

import org.junit.After;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class BaseScheduleTest {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @After
    public final void deleteAllJobs() throws SchedulerException {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        for (String jobGroup : scheduler.getJobGroupNames()) {
            for (String jobName : scheduler.getJobNames(jobGroup)) {
                scheduler.deleteJob(jobName, jobGroup);
            }
        }
    }

}
