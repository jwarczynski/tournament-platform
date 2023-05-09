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
        JobKey jobKey = new JobKey("startTournamentJob_" + tournamentId, "startTournamentJobGroup");
        return scheduler.checkExists(jobKey);
    }

    private void rescheduleJob(String tournamentId, Date startDate) throws SchedulerException {
        Trigger newTrigger = TriggerBuilder.newTrigger()
                .withIdentity(tournamentId, START_TOURNAMENT_TRIGGER_GROUP)
                .startAt(DateUtils.addMinutes(new Date(), 1))
                .build();
        scheduler.rescheduleJob(TriggerKey.triggerKey(tournamentId, START_TOURNAMENT_TRIGGER_GROUP), newTrigger);
    }

    private void createJob(String tournamentId, Date startDate) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(StartTournamentJob.class)
                .withIdentity("startTournamentJob_" + tournamentId, "startTournamentJobGroup")
                .build();

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
