package pl.warczynski.jedrzej.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.warczynski.jedrzej.backend.dao.DuelDao;
import pl.warczynski.jedrzej.backend.models.tournament.players.Player;
import pl.warczynski.jedrzej.backend.models.tournament.duel.Duel;
import pl.warczynski.jedrzej.backend.models.tournament.duel.status.DuelStatus;
import pl.warczynski.jedrzej.backend.services.interfaces.DuelService;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DuelServiceImpl implements DuelService {
    private final DuelDao duelDao;

    @Autowired
    public DuelServiceImpl(DuelDao duelDao) {
        this.duelDao = duelDao;
    }

    @Override
    public void saveDuels(List<Duel> duels) {
        duelDao.saveAll(duels);
    }

    @Override
    public List<Duel> getTournamentDuels(String tournamentId) {
        return duelDao.findByTournamentId(tournamentId);
    }


}
