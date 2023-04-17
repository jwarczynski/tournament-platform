package pl.warczynski.jedrzej.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "tournaments")
public class Tournament {
    @Id
    private String _id;
    private String name;
    private String discipline;
    private String organizer;
    private Date registrationDeadline;
    private Date date;
    private int registrationLimit;
    private String description;
    private String location;
    private String[] sponsors;
    private String mainImage;
    private String[] seedPlayers;

    public Tournament() {}

    public Tournament(String _id, String name, String discipline, String organizer, Date registrationDeadline, Date date,
                      int registrationLimit, String description, String location, String[] sponsors, String mainImage,
                      String[] seedPlayers) {
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

    // getters and setters for all fields
}
