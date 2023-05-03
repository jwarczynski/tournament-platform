package pl.warczynski.jedrzej.backend.services.impl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.warczynski.jedrzej.backend.Exceptions.signedUp.TournamentSignUpException;
import pl.warczynski.jedrzej.backend.dao.TournamentDao;
import pl.warczynski.jedrzej.backend.dto.tournament.SingUpFormDto;
import pl.warczynski.jedrzej.backend.models.tournament.players.Player;
import pl.warczynski.jedrzej.backend.models.tournament.Tournament;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionHelper {
    private TournamentDao tournamentDao;
    private TournamentSignUpValidator validator;

    @Autowired
    public TransactionHelper(TournamentDao tournamentDao, TournamentSignUpValidator signUpValidator) {
        this.tournamentDao = tournamentDao;
        this.validator = signUpValidator;
    }

    @Transactional
    public void enrollUser(SingUpFormDto singUpForm) {
        Tournament tournament = tournamentDao.findById(singUpForm.getTournamentId()).orElseThrow(
                () -> new TournamentSignUpException("The tournament does not exist."));

        signUpForTournament(singUpForm, tournament);
        tournamentDao.save(tournament);
    }

    private void signUpForTournament(SingUpFormDto singUpForm, Tournament tournament) {
        validateSignUp(singUpForm, tournament);
        addPlayerToTournament(singUpForm, tournament.getEnrolledPlayers());
    }

    private void validateSignUp(SingUpFormDto singUpForm, Tournament tournament) {
        if (tournament.getEnrolledPlayers() == null) {
            tournament.setEnrolledPlayers(new ArrayList<>());
        }

        validator.setRegistrationLimit(tournament.getRegistrationLimit());
        validator.setEnrolledPlayers(tournament.getEnrolledPlayers());
        validator.validate(singUpForm);
    }

    private void addPlayerToTournament(SingUpFormDto singUpForm, List<Player> enrolledPlayers) {
        Player newPlayer = new Player(singUpForm.getEmail(), singUpForm.getLicenseNumber(), singUpForm.getRanking());
        enrolledPlayers.add(newPlayer);
    }
}