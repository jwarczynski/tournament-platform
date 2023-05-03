package pl.warczynski.jedrzej.backend.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.warczynski.jedrzej.backend.models.tournament.Tournament;
import pl.warczynski.jedrzej.backend.services.impl.util.DuelsDrawer;
import pl.warczynski.jedrzej.backend.services.interfaces.DuelService;
import pl.warczynski.jedrzej.backend.services.interfaces.TournamentService;

@Component
public class StartTournamentJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(StartTournamentJob.class);
    @Autowired
    private DuelsDrawer duelsDrawer;
    @Autowired
    private DuelService duelService;
    @Autowired
    private TournamentService tournamentService;

    public StartTournamentJob() {}

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String tournamentId = (String) context.getJobDetail().getJobDataMap().get("tournamentId");
        Tournament tournament = tournamentService.getTournamentById(tournamentId).orElse(null);

        var duels = duelsDrawer.generateDuels(tournament.getEnrolledPlayers(),
                tournament.get_id(), tournament.getSeedPlayers());
        duelService.saveDuels(duels);

        logger.info("Started tournament with id {}", tournament.get_id());
    }

}
