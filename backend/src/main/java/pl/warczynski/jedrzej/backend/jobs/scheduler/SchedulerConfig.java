package pl.warczynski.jedrzej.backend.jobs.scheduler;

import org.apache.commons.lang3.time.DateUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;
import pl.warczynski.jedrzej.backend.jobs.StartTournamentJob;

import java.util.Date;

@Component
public class SchedulerConfig {

    private final Scheduler scheduler;

    private final JobDetailFactoryBean jobDetailFactory;

    @Autowired
    public SchedulerConfig(Scheduler scheduler, JobDetailFactoryBean jobDetailFactory) {
        this.scheduler = scheduler;
        this.jobDetailFactory = jobDetailFactory;
    }


    public void scheduleTournament(Date startDate, String tournamentId) throws SchedulerException {
        JobDetail jobDetail = jobDetailFactory.getObject();
        jobDetail.getJobDataMap().put("tournamentId", tournamentId);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("StartTournamentTrigger", "StartTournamentTriggerGroup")
                .startAt(DateUtils.addMinutes(new Date(), 1))
                .forJob(jobDetail)
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Autowired
    public JobDetailFactoryBean jobDetailFactory() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(StartTournamentJob.class);
        factory.setDescription("Invoke Start Tournament Job service...");
        factory.setDurability(true);
        return factory;
    }

    @Autowired
    public SimpleTriggerFactoryBean triggerFactory() {
        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
        factory.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        factory.setRepeatCount(0);
        return factory;
    }

    @Autowired
    public Scheduler scheduler() throws SchedulerException {
        return new StdSchedulerFactory().getScheduler();
    }
}
