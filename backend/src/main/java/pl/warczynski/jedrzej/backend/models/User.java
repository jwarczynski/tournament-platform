package pl.warczynski.jedrzej.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class User {
    @Id
    private String email;
    private String hash;
    private String salt;
    private String firstName;
    private String surname;
    private boolean active;
}
