package pl.warczynski.jedrzej.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.UUID;

public class VerificationToken {
    @Id
    private String id;
    @Indexed(unique = true)
    private String token;
    private LocalDateTime expiryDate;

    public VerificationToken() {
        token = UUID.randomUUID().toString();
        expiryDate = LocalDateTime.now().plusMinutes(15);
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
