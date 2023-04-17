package pl.warczynski.jedrzej.backend.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class OrdinaryUser extends User {
    public OrdinaryUser(String email, String hash, String salt, String firstName, String surname, boolean active, VerificationToken verificationToken) {
        super(email, hash, salt, firstName, surname, active, verificationToken);
    }
}
