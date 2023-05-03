package pl.warczynski.jedrzej.backend.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.warczynski.jedrzej.backend.dto.authentication.RegistrationDto;
import pl.warczynski.jedrzej.backend.dto.authentication.ResetPasswordDto;
import pl.warczynski.jedrzej.backend.dto.authentication.UserLoginDto;
import pl.warczynski.jedrzej.backend.models.user.User;

public interface UserService {

    User registerNewUser(RegistrationDto registrationDto);

    User verifyEmail(String verificationToken);

    ResponseEntity<User> login(UserLoginDto userLoginDto);

    ResponseEntity processForgotPassword(String email);

    ResponseEntity resetPassword(ResetPasswordDto resetPasswordDto);
}