package pl.warczynski.jedrzej.backend.dto.tournament;

public class SingUpFormDto {
    private String tournamentId;
    private String licenseNumber;
    private String email;
    private Integer ranking;

    public SingUpFormDto(String tournamentId, String licenseNumber, String email, Integer ranking) {
        this.tournamentId = tournamentId;
        this.licenseNumber = licenseNumber;
        this.email = email;
        this.ranking = ranking;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getEmail() {
        return email;
    }

    public Integer getRanking() {
        return ranking;
    }
}
