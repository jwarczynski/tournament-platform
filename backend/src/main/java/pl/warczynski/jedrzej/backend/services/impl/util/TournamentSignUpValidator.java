package pl.warczynski.jedrzej.backend.services.impl.util;

import org.springframework.stereotype.Component;
import pl.warczynski.jedrzej.backend.Exceptions.signedUp.TournamentSignUpException;
import pl.warczynski.jedrzej.backend.dto.tournament.SingUpFormDto;
import pl.warczynski.jedrzej.backend.models.tournament.players.Player;

import java.util.List;

@Component
public class TournamentSignUpValidator {
    private List<Player> enrolledPlayers;
    private Integer registrationLimit;

    public void validate(SingUpFormDto singUpForm) {
        if (enrolledPlayers.stream().anyMatch(player -> player.getEmail().equals(singUpForm.getEmail()))) {
            throw new TournamentSignUpException("User already signed up");
        }
        if (enrolledPlayers.size() >= registrationLimit) {
            throw new TournamentSignUpException("The tournament is full.");
        }
    }

    public void setEnrolledPlayers(List<Player> enrolledPlayers) {
        this.enrolledPlayers = enrolledPlayers;
    }

    public void setRegistrationLimit(Integer registrationLimit) {
        this.registrationLimit = registrationLimit;
    }
}
