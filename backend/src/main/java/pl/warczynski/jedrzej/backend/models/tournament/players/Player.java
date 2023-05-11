package pl.warczynski.jedrzej.backend.models.tournament.players;

import org.springframework.data.mongodb.core.index.Indexed;
import pl.warczynski.jedrzej.backend.models.tournament.duel.status.PlayerStatus;

import java.util.Objects;

public class Player {
    @Indexed(unique = true)
    private String email;
    private String licenseNumber;
    private Integer rank;
    private PlayerStatus playerStatus;

    public static Player createEmptyPlayer() {
        return new Player("", "", -1, PlayerStatus.LOOSE);
    }


    public Player(String email, String licenseNumber, Integer rank, PlayerStatus playerStatus) {
        this.email = email;
        this.licenseNumber = licenseNumber;
        this.rank = rank;
        this.playerStatus = playerStatus;
    }

    public Player(String email, String licenseNumber, Integer rank) {
        this.email = email;
        this.licenseNumber = licenseNumber;
        this.rank = rank;
    }

    public Player(Player playerToCopy) {
        this.email = playerToCopy.getEmail();
        this.licenseNumber = playerToCopy.getLicenseNumber();
        this.rank = playerToCopy.getRank();
        this.playerStatus = playerToCopy.getPlayerStatus();
    }


    public Player() {}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(email, player.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public String getEmail() {
        return email;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public Integer getRank() {
        return rank;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }
}


