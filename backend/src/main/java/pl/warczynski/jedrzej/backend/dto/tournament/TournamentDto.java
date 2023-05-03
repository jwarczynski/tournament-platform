package pl.warczynski.jedrzej.backend.dto.tournament;

import com.mongodb.lang.Nullable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public class TournamentDto {
    @Nullable
    private String _id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registrationDeadline;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String title;
    private String discipline;
    private String description;
    private String location;
    private String registrationLimit;
    private String organizer;
    private Integer seedPlayers;
    private List<MultipartFile> sponsorLogos;
    private MultipartFile mainImage;

    public TournamentDto(@Nullable String _id, Date registrationDeadline, Date date, String title, String discipline, String description, String location, String registrationLimit, String organizer, Integer seedPlayers, List<MultipartFile> sponsorLogos, MultipartFile mainImage) {
        this._id = _id;
        this.registrationDeadline = registrationDeadline;
        this.date = date;
        this.title = title;
        this.discipline = discipline;
        this.description = description;
        this.location = location;
        this.registrationLimit = registrationLimit;
        this.organizer = organizer;
        this.seedPlayers = seedPlayers;
        this.sponsorLogos = sponsorLogos;
        this.mainImage = mainImage;
    }

    @Nullable
    public String get_id() {
        return _id;
    }

    public Date getRegistrationDeadline() {
        return registrationDeadline;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getRegistrationLimit() {
        return registrationLimit;
    }

    public String getOrganizer() {
        return organizer;
    }

    public Integer getSeedPlayers() {
        return seedPlayers;
    }

    public List<MultipartFile> getSponsorLogos() {
        return sponsorLogos;
    }

    public MultipartFile getMainImage() {
        return mainImage;
    }
}

