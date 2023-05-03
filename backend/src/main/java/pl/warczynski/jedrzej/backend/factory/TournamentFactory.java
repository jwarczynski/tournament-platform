package pl.warczynski.jedrzej.backend.factory;

import pl.warczynski.jedrzej.backend.dto.tournament.TournamentDto;
import pl.warczynski.jedrzej.backend.models.tournament.Tournament;

import java.util.List;

public class TournamentFactory {
    public static Tournament createTournament(TournamentDto tournamentDto, List<String> sponsorsPaths, String mainImagePath) {
        return new Tournament(
                tournamentDto.get_id(),
                tournamentDto.getTitle(),
                tournamentDto.getDiscipline(),
                tournamentDto.getOrganizer(),
                tournamentDto.getRegistrationDeadline(),
                tournamentDto.getDate(),
                Integer.parseInt(tournamentDto.getRegistrationLimit()),
                tournamentDto.getDescription(),
                tournamentDto.getLocation(),
                sponsorsPaths,
                mainImagePath,
                tournamentDto.getSeedPlayers());
    }
}
