package pl.warczynski.jedrzej.backend.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.warczynski.jedrzej.backend.Exceptions.InvalidVerificationTokenException;
import pl.warczynski.jedrzej.backend.common.AccountStatus;
import pl.warczynski.jedrzej.backend.dao.UserDao;
import pl.warczynski.jedrzej.backend.dto.RegistrationDto;
import pl.warczynski.jedrzej.backend.dto.ResetPasswordDto;
import pl.warczynski.jedrzej.backend.dto.UserLoginDto;
import pl.warczynski.jedrzej.backend.factory.VerificationTokenFactory;
import pl.warczynski.jedrzej.backend.models.OrdinaryUser;
import pl.warczynski.jedrzej.backend.models.User;
import pl.warczynski.jedrzej.backend.models.VerificationToken;
import pl.warczynski.jedrzej.backend.services.interfaces.EmailService;
import pl.warczynski.jedrzej.backend.services.interfaces.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

import static pl.warczynski.jedrzej.backend.factory.VerificationTokenFactory.MethodType.EMAIL_VERIFICATION;
import static pl.warczynski.jedrzej.backend.factory.VerificationTokenFactory.MethodType.PASSWORD_RESET;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userDao = userDao;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User registerNewUser(RegistrationDto registrationDto) {
        String password = registrationDto.password();
        String salt = BCrypt.gensalt();
        String hashedPassword = passwordEncoder.encode(salt + password);
        VerificationToken verificationToken = VerificationTokenFactory.createToken(EMAIL_VERIFICATION);

        User newUser = new OrdinaryUser(
                registrationDto.email(),
                hashedPassword,
                salt,
                registrationDto.firstName(),
                registrationDto.surname(),
                AccountStatus.INACTIVE.getStatus(),
                verificationToken
        );
        emailService.sendVerificationEmail(registrationDto, verificationToken.getToken());
        return userDao.save(newUser);
    }

     @Override
    public User verifyEmail(String verificationToken) {
        Optional<User> optionalUser = userDao.findByVerificationToken_token(verificationToken);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(isTokenActive(user.getVerificationToken())) {
                user.setActive(AccountStatus.ACTIVE.getStatus());
                emailService.sendConfirmationEmail(user.getEmail());
                return userDao.save(user);
            }
            else {
                throw new InvalidVerificationTokenException("token expired");
            }
        } else {
            throw new InvalidVerificationTokenException("Invalid verification token");
        }
    }

    public boolean isTokenActive(VerificationToken token) {
        LocalDateTime expiryDate = token.getExpiryDate();
        LocalDateTime now = LocalDateTime.now();
        return expiryDate.isAfter(now);
    }

    @Override
    public ResponseEntity<User> login(UserLoginDto userLoginDto) {
        String email = userLoginDto.email();
        String password = userLoginDto.password();
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (isPasswordValid(password, user) && user.isActive()) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(null);
    }

    public ResponseEntity processForgotPassword(String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        VerificationToken resetPasswordToken = VerificationTokenFactory.createToken(PASSWORD_RESET);
        user.setResetPasswordToken(resetPasswordToken);
        userDao.save(user);
        logger.info("reset password token for email: {} has been saved", email);

        emailService.sendRestPasswordEmail(email, resetPasswordToken.getToken());
        logger.info("send email to: {} with reset password token: {}", email, resetPasswordToken);

        return ResponseEntity.ok().build();
    }

    private boolean isPasswordValid(String password, User user) {
        return BCrypt.checkpw(user.getSalt() + password, user.getHash());
    }

    public ResponseEntity resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = userDao.findByEmail(resetPasswordDto.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));
        if (isVerificationCodeValid(user.getResetPasswordToken(), resetPasswordDto.verificationCode())) {
            String salt = BCrypt.gensalt();
            String hashedPassword = passwordEncoder.encode(salt + resetPasswordDto.password());
            user.setHash(hashedPassword);
            user.setSalt(salt);
            userDao.save(user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(null);


    }

    private boolean isVerificationCodeValid(VerificationToken verificationToken, String userEnteredCode) {
        return userEnteredCode.equals(verificationToken.getToken()) &&
                isTokenActive(verificationToken);
    }
}