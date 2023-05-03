package pl.warczynski.jedrzej.backend.models.tournament.duel.status;

public enum DuelStatus {
    APPROVED("APPROVED"),
    INCONSISTENT("INCONSISTENT"),
    PENDING_QUORUM("PENDING_QUORUM"),
    NOT_PLAYED("NOT_PLAYED");

    private String status;

    DuelStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }


}
