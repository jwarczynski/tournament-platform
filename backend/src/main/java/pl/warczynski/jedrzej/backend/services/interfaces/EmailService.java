package pl.warczynski.jedrzej.backend.services.interfaces;

import pl.warczynski.jedrzej.backend.dto.authentication.RegistrationDto;

public interface EmailService {
  String PORTAL_EMAIL = "your.tournament.portal@gmail.com";
  String VERIFICATION_SUBJECT = "Email Verification";
  String RESET_PASSWORD_SUBJECT = "Password reset";
  String CONFIRMATION_SUBJECT = "Email Verification Confirmed";
  String CONFIRMATION_CONTENT = "your email has been successfully verified";
  String RESET_PASSWORD_CONTENT = "your reset password code: ";

  void sendVerificationEmail(RegistrationDto registrationDto, String verificationToken);
  void sendConfirmationEmail(String email);
  void sendRestPasswordEmail(String email, String token);

  }
