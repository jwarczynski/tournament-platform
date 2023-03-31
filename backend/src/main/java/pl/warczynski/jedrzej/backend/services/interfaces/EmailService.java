package pl.warczynski.jedrzej.backend.services.interfaces;

import org.springframework.stereotype.Service;
import pl.warczynski.jedrzej.backend.dto.RegistrationDto;

public interface EmailService {
  String PORTAL_EMAIL = "your.tournament.portal@gmail.com";
  String VERIFICATION_SUBJECT = "Email Verification";
  String CONFIRMATION_SUBJECT = "Email Verification Confirmed";
  String CONFIRMATION_CONTENT = "your email has been successfully verified";

  public void sendVerificationEmail(RegistrationDto registrationDto);
}
