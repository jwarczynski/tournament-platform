package pl.warczynski.jedrzej.backend.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.warczynski.jedrzej.backend.models.tournament.Tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentDao extends MongoRepository<Tournament, String> { 
    
    Optional<Tournament> findById(String id);

    List<Tournament> findAll();
    
    Tournament save(Tournament tournament);


}