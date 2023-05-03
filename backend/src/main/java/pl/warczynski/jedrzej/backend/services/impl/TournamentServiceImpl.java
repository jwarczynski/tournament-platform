package pl.warczynski.jedrzej.backend.services.impl;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;
import pl.warczynski.jedrzej.backend.Exceptions.signedUp.TournamentSignUpException;
import pl.warczynski.jedrzej.backend.dao.TournamentDao;
import pl.warczynski.jedrzej.backend.dto.tournament.SingUpFormDto;
import pl.warczynski.jedrzej.backend.dto.tournament.TournamentDto;
import pl.warczynski.jedrzej.backend.factory.TournamentFactory;
import pl.warczynski.jedrzej.backend.jobs.scheduler.SchedulerConfig;
import pl.warczynski.jedrzej.backend.models.tournament.Tournament;
import pl.warczynski.jedrzej.backend.services.impl.util.TransactionHelper;
import pl.warczynski.jedrzej.backend.services.interfaces.FileService;
import pl.warczynski.jedrzej.backend.services.interfaces.TournamentService;

import java.util.*;


@Service
public class TournamentServiceImpl implements TournamentService {
    private static final Logger logger = LoggerFactory.getLogger(TournamentServiceImpl.class);

    private final TournamentDao tournamentDao;
    private final FileService fileService;
    private final TransactionHelper transactionHelper;
    @Autowired
    SchedulerConfig schedulerConfig;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    public TournamentServiceImpl(TournamentDao tournamentDao, FileService fileService,
                                 TransactionHelper transactionHelper) {
        this.tournamentDao = tournamentDao;
        this.fileService = fileService;
        this.transactionHelper = transactionHelper;
    }

    public List<Tournament> getAllTournaments() {
        return tournamentDao.findAll();
    }

    @Override
    public Optional<Tournament> getTournamentById(String id) {
        return tournamentDao.findById(id);
    }

    @Override
    public Tournament save(TournamentDto tournamentDto) {
        String mainImagePath = fileService.saveFile(tournamentDto.getMainImage());
        List<String> sponsorsPaths = saveSponsorsImages(tournamentDto.getSponsorLogos());
        Tournament tournament = TournamentFactory.createTournament(tournamentDto, sponsorsPaths, mainImagePath);
        return saveAndScheduleJob(tournament);
    }

    private Tournament saveAndScheduleJob(Tournament tournament) {
        return transactionTemplate.execute(status -> {
            try {
                Tournament savedTournament = tournamentDao.save(tournament);
                schedulerConfig.scheduleTournament(new Date(), savedTournament.get_id());
                return savedTournament;
            } catch (SchedulerException e) {
                logger.error("error during scheduling job");
                status.setRollbackOnly();
                return null;
            }
        });
    }

    private List<String> saveSponsorsImages(List<MultipartFile> sponsors) {
        List<String> sponsorsPaths = new ArrayList<>();
        if (sponsors != null) {
            for (MultipartFile sponsorLogo : sponsors) {
                sponsorsPaths.add(fileService.saveFile(sponsorLogo));
            }
        }
        return sponsorsPaths;
    }

    public ResponseEntity<Map<String, String>> signUp(SingUpFormDto singUpForm) {
        Map<String, String> response = new HashMap<>();
        try {
            transactionHelper.enrollUser(singUpForm);
            logger.info("user {} successfully enrolled for tournament with id {}", singUpForm.getEmail(), singUpForm.getTournamentId());
            response.put("message", "user enrolled");
            return ResponseEntity.ok().body(response);
        } catch (TournamentSignUpException e) {
            logger.info(e.getMessage());
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


}
