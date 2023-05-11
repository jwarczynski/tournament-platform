package pl.warczynski.jedrzej.backend.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.warczynski.jedrzej.backend.dto.tournament.SingUpFormDto;
import pl.warczynski.jedrzej.backend.dto.tournament.TournamentDto;
import pl.warczynski.jedrzej.backend.models.tournament.Tournament;
import pl.warczynski.jedrzej.backend.services.interfaces.TournamentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/tournaments")
@CrossOrigin(origins = "http://localhost:4200")
public class TournamentRestEndpoint {
    public static final Logger logger = LoggerFactory.getLogger(TournamentRestEndpoint.class);
    private static final String IMAGE_DIR = "uploaded-images";
    private final TournamentService tournamentService;

    @Autowired
    public TournamentRestEndpoint(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping()
    public ResponseEntity<List<Tournament>> getTournaments() {
        logger.info("called get on api/tournaments");
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournament(@PathVariable String id) {
        logger.info("called get on api/tournaments/{}", id);
        Tournament tournament = tournamentService.getTournamentById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tournament not found"));
        return ResponseEntity.ok(tournament);
    }

    @PostMapping(value = "/save", consumes = {"multipart/form-data"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tournament> saveTournament(@ModelAttribute TournamentDto tournamentDto) {
        logger.info("Called post on api/tournaments/save");
        Tournament savedTournament = tournamentService.save(tournamentDto);
        if (savedTournament == null) {
            logger.error("Failed to create tournament");
            return ResponseEntity.status(500).build();
        }
        logger.info("Created new tournament with id {}", savedTournament.get_id());
        return ResponseEntity.ok().body(savedTournament);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SingUpFormDto signupForm) {
        logger.info("called post on tournaments.signup");
        return tournamentService.signUp(signupForm);
    }

    @PostMapping(value = "/test", consumes = {"multipart/form-data"})
    public ResponseEntity<String> myEndpoint(HttpServletRequest req) {
        return ResponseEntity.ok().body("test");
    }

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> getTournamentImage(@RequestParam("filename") String filename) {
        try {
            Path path = Paths.get(IMAGE_DIR, filename);
            byte[] imageBytes = Files.readAllBytes(path);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
