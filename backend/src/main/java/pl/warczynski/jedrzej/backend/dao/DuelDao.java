package pl.warczynski.jedrzej.backend.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.warczynski.jedrzej.backend.models.tournament.duel.Duel;

import java.util.List;
import java.util.Optional;

@Repository
public interface DuelDao extends MongoRepository<Duel, String> {

    Optional<Duel> findById(String id);

    List<Duel> findByTournamentId(String tournamentId);
    @Query("{tournamentId: ?0, phase: ?1, duelNumber: ?2}")
    Optional<Duel> findByTournamentStage(String tournamentId, int phase, int duelNumber);

    List<Duel> findAll();

    @Query("{ $or : [{'player1.email' : ?0}, {'player2.email': ?0}]}")
    List<Duel> findByPlayerEmail(String email);

    <T extends Duel> T save(T duel);

    @Override
    <S extends Duel> List<S> saveAll(Iterable<S> entities);
}

