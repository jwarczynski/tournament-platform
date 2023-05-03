package pl.warczynski.jedrzej.backend.services.interfaces;

import pl.warczynski.jedrzej.backend.models.tournament.duel.Duel;

import java.util.List;

public interface DuelService {
    public void saveDuels(List<Duel> duels);

    public List<Duel> getTournamentDuels(String tournamentId);
}
