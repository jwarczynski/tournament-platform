package pl.warczynski.jedrzej.backend.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.warczynski.jedrzej.backend.dto.RegistrationDto;
import pl.warczynski.jedrzej.backend.dto.ResetPasswordDto;
import pl.warczynski.jedrzej.backend.dto.UserLoginDto;
import pl.warczynski.jedrzej.backend.models.User;

public interface UserService {

    User registerNewUser(RegistrationDto registrationDto);

    User verifyEmail(String verificationToken);

    ResponseEntity<User> login(UserLoginDto userLoginDto);

    ResponseEntity processForgotPassword(String email);

    ResponseEntity resetPassword(ResetPasswordDto resetPasswordDto);
}