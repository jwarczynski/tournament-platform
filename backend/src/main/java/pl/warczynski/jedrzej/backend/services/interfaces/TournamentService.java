package pl.warczynski.jedrzej.backend.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.warczynski.jedrzej.backend.dto.tournament.SingUpFormDto;
import pl.warczynski.jedrzej.backend.dto.tournament.TournamentDto;
import pl.warczynski.jedrzej.backend.models.tournament.Tournament;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TournamentService {
    List<Tournament> getAllTournaments();

    Optional<Tournament> getTournamentById(String id);

    Tournament save(TournamentDto tournament);

    ResponseEntity<Map<String,String>> signUp(SingUpFormDto singUpForm);
}
