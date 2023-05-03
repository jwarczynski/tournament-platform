package pl.warczynski.jedrzej.backend.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.warczynski.jedrzej.backend.dto.authentication.RegistrationDto;
import pl.warczynski.jedrzej.backend.dto.authentication.ResetPasswordDto;
import pl.warczynski.jedrzej.backend.dto.authentication.UserLoginDto;
import pl.warczynski.jedrzej.backend.models.user.User;
import pl.warczynski.jedrzej.backend.services.interfaces.UserService;

@RestController()
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestEndpoint {
  public static final Logger logger = LoggerFactory.getLogger(UserRestEndpoint.class);
  private final UserService userService;

  @Autowired
  public UserRestEndpoint(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<Void> registerUser(@RequestBody RegistrationDto registrationDto) {
    logger.info("registration request: {}", registrationDto);
    userService.registerNewUser(registrationDto);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/register")
  public ResponseEntity<Void> completeVerification(@RequestParam("token") String token) {
    logger.info("token {}", token);
    userService.verifyEmail(token);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<User> login(@RequestBody UserLoginDto userLoginDto) {
    return userService.login(userLoginDto);
  }

  @GetMapping("/forgot-password")
  public ResponseEntity<?> processForgotPassword(@RequestParam("email") String email) {
    userService.processForgotPassword(email);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
    return userService.resetPassword(resetPasswordDto);
  }

}
