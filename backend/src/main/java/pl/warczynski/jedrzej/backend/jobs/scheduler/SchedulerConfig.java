package pl.warczynski.jedrzej.backend.jobs.scheduler;

import org.apache.commons.lang3.time.DateUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.warczynski.jedrzej.backend.jobs.StartTournamentJob;

import java.util.Date;

@Component
public class SchedulerConfig {
    public static final String TOURNAMENT_CONTEXT_KEY = "tournament";
    public static final String START_TOURNAMENT_JOB_PREFIX = "startTournamentJob_";
    private static final String TOURNAMENT_JOB_GROUP = "startTournamentJobGroup";
    private static final String START_TOURNAMENT_TRIGGER_GROUP = "StartTournamentTriggerGroup";

    private final Scheduler scheduler;

    @Autowired
    public SchedulerConfig(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void scheduleTournament(Date startDate, String tournamentId) throws SchedulerException {
        if (jobExists(tournamentId)) {
           rescheduleJob(tournamentId, startDate);
        } else {
            createJob(tournamentId, startDate);
        }
    }

    private boolean jobExists(String tournamentId) throws SchedulerException {
        JobKey jobKey = new JobKey(START_TOURNAMENT_JOB_PREFIX + tournamentId, TOURNAMENT_JOB_GROUP);
        return scheduler.checkExists(jobKey);
    }

    private void rescheduleJob(String tournamentId, Date startDate) throws SchedulerException {
        JobKey jobKey = new JobKey(START_TOURNAMENT_JOB_PREFIX + tournamentId, TOURNAMENT_JOB_GROUP);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put(TOURNAMENT_CONTEXT_KEY, tournamentId);

        Trigger newTrigger = TriggerBuilder.newTrigger()
                .withIdentity(tournamentId, START_TOURNAMENT_TRIGGER_GROUP)
                .startAt(DateUtils.addMinutes(new Date(), 1))
                .forJob(jobDetail)
                .build();
        scheduler.rescheduleJob(TriggerKey.triggerKey(tournamentId, START_TOURNAMENT_TRIGGER_GROUP), newTrigger);
    }


    private void createJob(String tournamentId, Date startDate) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(StartTournamentJob.class)
                .withIdentity(START_TOURNAMENT_JOB_PREFIX + tournamentId, TOURNAMENT_JOB_GROUP)
                .build();
        jobDetail.getJobDataMap().put(TOURNAMENT_CONTEXT_KEY, tournamentId);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(tournamentId, START_TOURNAMENT_TRIGGER_GROUP)
                .startAt(DateUtils.addMinutes(new Date(), 1))
                .forJob(jobDetail)
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }


    @Autowired
    public Scheduler scheduler() throws SchedulerException {
        return new StdSchedulerFactory().getScheduler();
    }
}
