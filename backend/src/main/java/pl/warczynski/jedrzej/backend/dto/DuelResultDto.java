package pl.warczynski.jedrzej.backend.dto;

import pl.warczynski.jedrzej.backend.models.tournament.duel.Duel;

public class DuelResultDto {
    private Duel duel;
    private String applicantEmail;

    public Duel getDuel() {
        return duel;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }
}
