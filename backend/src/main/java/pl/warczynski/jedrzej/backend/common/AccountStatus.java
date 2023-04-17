package pl.warczynski.jedrzej.backend.common;

public enum AccountStatus {
    ACTIVE(true),
    INACTIVE(false);

    private boolean active;

    AccountStatus(boolean active) {
        this.active = active;
    }

    public boolean getStatus() {
        return this.active;
    }
}
