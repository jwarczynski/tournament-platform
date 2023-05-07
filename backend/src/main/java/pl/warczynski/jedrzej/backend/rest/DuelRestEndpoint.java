package pl.warczynski.jedrzej.backend.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.warczynski.jedrzej.backend.dto.DuelResultDto;
import pl.warczynski.jedrzej.backend.models.tournament.duel.Duel;
import pl.warczynski.jedrzej.backend.services.interfaces.DuelService;

import java.util.List;

@RestController
@RequestMapping(value = "/duels")
@CrossOrigin(origins = "http://localhost:4200")
public class DuelRestEndpoint {
    public static final Logger logger = LoggerFactory.getLogger(DuelRestEndpoint.class);
    private final DuelService duelService;

    @Autowired
    public DuelRestEndpoint(DuelService duelService) {
        this.duelService = duelService;
    }

    @GetMapping("/tournament")
    public List<Duel> getTournaments(@RequestParam("tournament") String tournamentId) {
        logger.info("called get on api/duels with request param {}", tournamentId);
        return duelService.getTournamentDuels(tournamentId);
    }

    @GetMapping()
    public List<Duel> getUserDuels(@RequestParam("user") String email) {
        return duelService.getUserDuels(email);
    }

    @PostMapping("/save-result")
    public Duel updateResult(@RequestBody DuelResultDto duelResultDto) {
        return duelService.updateResult(duelResultDto.getDuel(), duelResultDto.getApplicantEmail());
    }
}
