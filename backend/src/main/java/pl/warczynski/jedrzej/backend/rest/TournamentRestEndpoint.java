package pl.warczynski.jedrzej.backend.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.warczynski.jedrzej.backend.models.Tournament;
import pl.warczynski.jedrzej.backend.services.interfaces.TournamentService;

@RestController
@RequestMapping(value = "/tournaments", consumes = "application/json", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
public class TournamentRestEndpoint {
    public static final Logger logger = LoggerFactory.getLogger(TournamentRestEndpoint.class);

    private final TournamentService tournamentService;

    @Autowired
    public TournamentRestEndpoint(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping()
    public ResponseEntity<Void> getTournaments() {
        logger.info("called get on api/tournaments");
        tournamentService.getAllTournaments();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournament(@PathVariable String id) {
        logger.info("called get on api/tournaments/{}", id);
        Tournament tournament = tournamentService.getTournamentById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tournament not found"));
        return ResponseEntity.ok(tournament);

    }

    @PostMapping("/save")
    public ResponseEntity<Tournament> saveTournament(@RequestBody Tournament tournament) {
        tournamentService.save(tournament);
        return ResponseEntity.ok(tournament);
    }


}
