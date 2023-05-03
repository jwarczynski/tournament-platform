package pl.warczynski.jedrzej.backend.models.tournament.duel.status;

public enum PlayerStatus {
    LOOSE("LOOSE"),
    WIN("WIN"),
    PENDING_DUEL("PENDING_DUEL"),
    DURING_GAMEPLAY("DURING_GAMEPLAY");

    private String status;

    PlayerStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

}
