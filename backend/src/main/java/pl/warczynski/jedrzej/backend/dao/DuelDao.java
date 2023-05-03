package pl.warczynski.jedrzej.backend.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.warczynski.jedrzej.backend.models.tournament.duel.Duel;
import pl.warczynski.jedrzej.backend.models.tournament.duel.status.PlayerStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface DuelDao extends MongoRepository<Duel, String> {

    Optional<Duel> findById(String id);

    List<Duel> findAll();

    List<Duel> findByTournamentId(String tournamentId);

    Duel save(Duel duel);

    @Override
    <S extends Duel> List<S> saveAll(Iterable<S> entities);

//    Optional<Duel> findByTournamentIdAndWinnerStatus(String id, PlayerStatus status);


}

