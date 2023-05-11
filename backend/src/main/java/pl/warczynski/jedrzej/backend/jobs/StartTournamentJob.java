package pl.warczynski.jedrzej.backend.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.warczynski.jedrzej.backend.models.tournament.Tournament;
import pl.warczynski.jedrzej.backend.models.tournament.players.Player;
import pl.warczynski.jedrzej.backend.services.impl.util.DuelsDrawer;
import pl.warczynski.jedrzej.backend.services.interfaces.DuelService;
import pl.warczynski.jedrzej.backend.services.interfaces.TournamentService;

import java.util.List;

import static pl.warczynski.jedrzej.backend.jobs.scheduler.SchedulerConfig.TOURNAMENT_CONTEXT_KEY;

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
    public void execute(JobExecutionContext context) {
        String tournamentId = (String) context.getJobDetail().getJobDataMap().get(TOURNAMENT_CONTEXT_KEY);
        Tournament tournament = tournamentService.getTournamentById(tournamentId).orElse(null);

        assert tournament != null;
        List<Player> enrolledPlayers = tournament.getEnrolledPlayers();
        int registrationLimit = tournament.getRegistrationLimit();
        if (enrolledPlayers.size() > registrationLimit) {
            enrolledPlayers = enrolledPlayers.subList(0, registrationLimit);
        }

        var duels = duelsDrawer.generateDuels(enrolledPlayers,
                tournament.get_id(), tournament.getSeedPlayers());
        duelService.saveDuels(duels);

        logger.info("Started tournament with id {}", tournament.get_id());
    }

}
