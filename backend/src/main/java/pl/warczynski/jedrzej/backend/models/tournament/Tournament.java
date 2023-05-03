package pl.warczynski.jedrzej.backend.models.tournament;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import pl.warczynski.jedrzej.backend.models.tournament.players.Player;

import java.io.Serializable;
import java.util.*;

@Document(collection = "tournaments")
public class Tournament implements Serializable {
    @Id
    private String _id;
    private String name;
    private String discipline;
    private String organizer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registrationDeadline;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private int registrationLimit;
    private String description;
    private String location;
    private List<String> sponsors;
    private String mainImage;
    private Integer seedPlayers;
    private List<Player> enrolledPlayers;

    public Tournament(String _id, String name, String discipline, String organizer, Date registrationDeadline, Date date, int registrationLimit, String description, String location, List<String> sponsors, String mainImage, Integer seedPlayers, List<Player> enrolledPlayers) {
        this._id = _id;
        this.name = name;
        this.discipline = discipline;
        this.organizer = organizer;
        this.registrationDeadline = registrationDeadline;
        this.date = date;
        this.registrationLimit = registrationLimit;
        this.description = description;
        this.location = location;
        this.sponsors = sponsors;
        this.mainImage = mainImage;
        this.seedPlayers = seedPlayers;
        this.enrolledPlayers = enrolledPlayers;
    }

    public Tournament(String _id, String name, String discipline, String organizer, Date registrationDeadline, Date date, int registrationLimit, String description, String location, List<String> sponsors, String mainImage, Integer seedPlayers) {
        this._id = _id;
        this.name = name;
        this.discipline = discipline;
        this.organizer = organizer;
        this.registrationDeadline = registrationDeadline;
        this.date = date;
        this.registrationLimit = registrationLimit;
        this.description = description;
        this.location = location;
        this.sponsors = sponsors;
        this.mainImage = mainImage;
        this.seedPlayers = seedPlayers;
    }

    public Tournament() {}

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getOrganizer() {
        return organizer;
    }

    public Date getRegistrationDeadline() {
        return registrationDeadline;
    }

    public Date getDate() {
        return date;
    }

    public int getRegistrationLimit() {
        return registrationLimit;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getSponsors() {
        return sponsors;
    }

    public String getMainImage() {
        return mainImage;
    }

    public Integer getSeedPlayers() {
        return seedPlayers;
    }

    public List<Player> getEnrolledPlayers() {
        return enrolledPlayers;
    }

    public void setEnrolledPlayers(List<Player> enrolledPlayers) {
        this.enrolledPlayers = enrolledPlayers;
    }
}
