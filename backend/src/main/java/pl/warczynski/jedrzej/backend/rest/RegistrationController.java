package pl.warczynski.jedrzej.backend.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.warczynski.jedrzej.backend.dto.RegistrationDto;
import pl.warczynski.jedrzej.backend.services.interfaces.EmailService;
import pl.warczynski.jedrzej.backend.services.interfaces.UserService;

@RestController()
@RequestMapping("/register")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {
  public static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
  private final EmailService emailService;
//  private final UserService userService;

  @Autowired
  public RegistrationController(EmailService emailService/*, UserService userService*/) {
    this.emailService = emailService;
//    this.userService = userService;
  }

  @PostMapping()
  public ResponseEntity<Void> registerUser(@RequestBody RegistrationDto registrationDto) {
    logger.info("registration request: {}", registrationDto);
//    userService.registerNewUser(registrationDto);
    emailService.sendVerificationEmail(registrationDto);
    return ResponseEntity.ok().build();
  }

  @GetMapping()
  public ResponseEntity<Void> completeVerification(@RequestParam("token") String token) {
    logger.info("token {}", token);
    return ResponseEntity.ok().build();
  }

}
