package pl.warczynski.jedrzej.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.warczynski.jedrzej.backend.dao.TournamentDao;
import pl.warczynski.jedrzej.backend.models.Tournament;
import pl.warczynski.jedrzej.backend.services.interfaces.TournamentService;

import java.util.List;
import java.util.Optional;


@Service
public class TournamentServiceImpl implements TournamentService {

    private final TournamentDao tournamentDao;

    @Autowired
    public TournamentServiceImpl(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

    public List<Tournament> getAllTournaments() {
        return tournamentDao.findAll();
    }

    @Override
    public Optional<Tournament> getTournamentById(String id) {
        return tournamentDao.findById(id);
    }

    @Override
    public Tournament save(Tournament tournament) {
        return tournamentDao.save(tournament);
    }
}
