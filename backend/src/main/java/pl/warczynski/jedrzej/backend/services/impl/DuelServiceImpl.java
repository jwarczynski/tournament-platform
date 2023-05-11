package pl.warczynski.jedrzej.backend.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.warczynski.jedrzej.backend.Exceptions.NoNextDuelException;
import pl.warczynski.jedrzej.backend.dao.DuelDao;
import pl.warczynski.jedrzej.backend.models.tournament.duel.Duel;
import pl.warczynski.jedrzej.backend.models.tournament.duel.status.DuelStatus;
import pl.warczynski.jedrzej.backend.models.tournament.duel.status.PlayerStatus;
import pl.warczynski.jedrzej.backend.models.tournament.players.Player;
import pl.warczynski.jedrzej.backend.services.interfaces.DuelService;

import java.util.List;
import java.util.Optional;

@Service
public class DuelServiceImpl implements DuelService {
    private static final Logger logger = LoggerFactory.getLogger(DuelServiceImpl.class);
    private final DuelDao duelDao;

    @Autowired
    public DuelServiceImpl(DuelDao duelDao) {
        this.duelDao = duelDao;
    }

    @Override
    public void saveDuels(List<Duel> duels) {
        duelDao.saveAll(duels);
    }

    @Override
    public List<Duel> getTournamentDuels(String tournamentId) {
        return duelDao.findByTournamentId(tournamentId);
    }

    @Override
    public List<Duel> getUserDuels(String email) {
        return duelDao.findByPlayerEmail(email);
    }

    @Override
    public ResponseEntity<Duel> updateResult(Duel duel, String applicantEmail) {
        Duel existingDuel = getDuelById(duel.getId());
        if (isFirstResultSubmission(existingDuel.getWinner())) {
            Duel updatedDuel =  saveAndWaitForQuorum(duel, applicantEmail);
            return ResponseEntity.ok(updatedDuel);
        }
        if (!areResultsConsistent(duel, existingDuel)) {
            Duel updatedDuel = updateDuelToInconsistent(existingDuel);
            return ResponseEntity.badRequest().body(updatedDuel);
        }
        return ResponseEntity.ok(saveAndPromoteWinner(duel));
    }

    private Duel getDuelById(String duelId) {
        return duelDao.findById(duelId).orElseThrow(() -> new RuntimeException("duel not exist"));
    }

    private boolean isFirstResultSubmission(Player winner) {
        return Player.createEmptyPlayer().equals(winner);
    }

    private Duel saveAndWaitForQuorum(Duel duel, String applicantEmail) {
        Player player = getPlayerFromDuel(duel, applicantEmail);
        player.setPlayerStatus(PlayerStatus.PENDING_DUEL);
        duel.setDuelStatus(DuelStatus.PENDING_QUORUM);
        return duelDao.save(duel);
    }

    private Player getPlayerFromDuel(Duel duel, String email) {
        if (email.equals(duel.getPlayer1().getEmail())) {
            return duel.getPlayer1();
        }
        return duel.getPlayer2();
//        TODO:
//        else if (email.equals(duel.getPlayer1())) {
//            return duel.getPlayer2();
//        } else {
//            throw new IllegalStateException("Unauthorized duel update");
//        }
    }

    private boolean areResultsConsistent(Duel duel, Duel existingDuel) {
        Player priorWinner = existingDuel.getWinner();
        Player newWinner = duel.getWinner();
        return isWinnerNotSpecified(priorWinner) || areSamePlayers(newWinner, priorWinner);
//        TODO:
//        if (isWinnerNotSpecified(priorWinner)) {
//            throw  new IllegalStateException("prior winner should be specified in second submission");
//        }
    }

    private boolean isWinnerNotSpecified(Player potentialWinner) {
        return potentialWinner == null || potentialWinner.equals(Player.createEmptyPlayer());
    }

    private boolean areSamePlayers(Player player1, Player player2) {
        return player1.getEmail().equals(player2.getEmail());
    }

    private Duel updateDuelToInconsistent(Duel duel) {
        duel.setDuelStatus(DuelStatus.INCONSISTENT);
        duel.setWinner(Player.createEmptyPlayer());
        duel.getPlayer1().setPlayerStatus(PlayerStatus.DURING_GAMEPLAY);
        duel.getPlayer2().setPlayerStatus(PlayerStatus.DURING_GAMEPLAY);
         return duelDao.save(duel);
    }

    private Duel saveAndPromoteWinner(Duel duel) {
        Player winner = getWinner(duel);
        Player looser = getLooser(duel);
        updatePlayersStatuses(winner, looser);
        duel.setDuelStatus(DuelStatus.APPROVED);
        Duel resolvedDuel = duelDao.save(duel);
        promoteWinnerToNextPhase(resolvedDuel);
        return resolvedDuel;
    }

    private Player getWinner(Duel duel) {
        if (duel.getWinner().equals(duel.getPlayer1())) {
            return duel.getPlayer1();
        } else if (duel.getWinner().equals(duel.getPlayer2())) {
            return duel.getPlayer2();
        } else if (duel.getWinner().equals(Player.createEmptyPlayer())){
            return Player.createEmptyPlayer();
        } else {
            throw new IllegalStateException("Winner template not specified");
        }
    }
    private Player getLooser(Duel duel) {
        if (duel.getWinner().equals(duel.getPlayer1())) {
            return duel.getPlayer2();
        } else if (duel.getWinner().equals(duel.getPlayer2())) {
            return duel.getPlayer1();
        } else {
            throw new IllegalStateException("Winner not specified");
        }
    }

    private void updatePlayersStatuses(Player winner, Player looser) {
        winner.setPlayerStatus(PlayerStatus.WIN);
        looser.setPlayerStatus(PlayerStatus.LOOSE);
    }

    private void promoteWinnerToNextPhase(Duel duel) {
        try {
            Duel nextPhaseDuel = getNextPhaseDuel(duel).orElseThrow(NoNextDuelException::new);
            Integer playerNumberInNextDuel = getPlayerNumberInDuel(duel.getDuelNumber());
            updatePlayerInNextPhaseDuel(nextPhaseDuel, playerNumberInNextDuel, duel.getWinner());
        } catch (NoNextDuelException nne) {
            logger.debug("Updating final duel");
        }
    }

    private Optional<Duel> getNextPhaseDuel(Duel previousDuel) {
        Integer phase = getNextPhase(previousDuel.getPhase());
        Integer duelNumber = getNextPhaseDuelNumber(previousDuel.getDuelNumber());
        return getDuelByTournamentStage(previousDuel.getTournamentId(), phase, duelNumber);
    }

    private Integer getNextPhase(Integer phase) {
        return phase + 1;
    }

    private Integer getNextPhaseDuelNumber(Integer duelNumber) {
        return duelNumber / 2;
    }

    private Optional<Duel> getDuelByTournamentStage(String tournamentId, Integer phase, Integer duelNumber) {
        return duelDao.findByTournamentStage(tournamentId, phase, duelNumber);
    }

    private Integer getPlayerNumberInDuel(Integer duelNumber) {
        return duelNumber % 2;
    }

    private void updatePlayerInNextPhaseDuel(Duel duel, Integer playerNumber, Player winner) {
        if (playerNumber == 0) {
            duel.setPlayer1(winner);
        } else {
            duel.setPlayer2(winner);
        }
        duelDao.save(duel);
    }

}
