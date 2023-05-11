package pl.warczynski.jedrzej.backend.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.warczynski.jedrzej.backend.models.tournament.duel.Duel;

import java.util.List;

public interface DuelService {
    void saveDuels(List<Duel> duels);
    ResponseEntity<Duel> updateResult(Duel duel, String applicantEmail);
    List<Duel> getTournamentDuels(String tournamentId);
    List<Duel> getUserDuels(String email);
}
