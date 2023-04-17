package pl.warczynski.jedrzej.backend.services.interfaces;

import pl.warczynski.jedrzej.backend.models.Tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentService {
    List<Tournament> getAllTournaments();

    Optional<Tournament> getTournamentById(String id);

    Tournament save(Tournament tournament);
}
