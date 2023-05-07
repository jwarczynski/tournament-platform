package pl.warczynski.jedrzej.backend.models.tournament.duel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.warczynski.jedrzej.backend.models.tournament.duel.status.DuelStatus;
import pl.warczynski.jedrzej.backend.models.tournament.players.Player;

@Document(collection = "duels")
public class Duel {
    @Id
    private String id;
    private String tournamentId;
    private Player player1;
    private Player player2;
    private Player winner;
    private DuelStatus duelStatus;
    private Integer phase;
    private Integer duelNumber;

    public Duel(String tournamentId, Player player1, Player player2, Player winner, DuelStatus duelStatus, Integer phase, Integer duelNumber) {
        this.tournamentId = tournamentId;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.duelStatus = duelStatus;
        this.phase = phase;
        this.duelNumber = duelNumber;
    }

    public Duel(String tournamentId, Player player1, Player player2, DuelStatus duelStatus, Integer phase, Integer duelNumber) {
        this.tournamentId = tournamentId;
        this.player1 = player1;
        this.player2 = player2;
        this.duelStatus = duelStatus;
        this.phase = phase;
        this.duelNumber = duelNumber;
    }

    public Duel() {
    }

    public String getId() {
        return id;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getWinner() {
        return winner;
    }

    public DuelStatus getDuelStatus() {
        return duelStatus;
    }

    public Integer getPhase() {
        return phase;
    }

    public Integer getDuelNumber() {
        return duelNumber;
    }

    public void setDuelStatus(DuelStatus duelStatus) {
        this.duelStatus = duelStatus;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
