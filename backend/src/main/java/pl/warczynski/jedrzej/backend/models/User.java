package pl.warczynski.jedrzej.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public abstract class User {
    @Id
    protected String id;
    @Indexed(unique = true)
    protected String email;
    protected String hash;
    protected String salt;
    protected String firstName;
    protected String surname;
    protected boolean active;
    protected VerificationToken verificationToken;
    protected VerificationToken resetPasswordToken;

    public User(String email, String hash, String salt, String firstName, String surname, boolean active, VerificationToken verificationToken) {
        this.email = email;
        this.hash = hash;
        this.salt = salt;
        this.firstName = firstName;
        this.surname = surname;
        this.active = active;
        this.verificationToken = verificationToken;
    }

    public String getEmail() {
        return email;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public VerificationToken getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(VerificationToken resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
