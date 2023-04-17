package pl.warczynski.jedrzej.backend.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.warczynski.jedrzej.backend.models.User;

import java.util.Optional;

@Repository
public interface UserDao extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
    @Query("{ 'verificationToken.token' : ?0 }")
    Optional<User> findByVerificationToken_token(String verificationToken);

    User save(User user);

}
