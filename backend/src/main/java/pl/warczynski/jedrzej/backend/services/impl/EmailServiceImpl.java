package pl.warczynski.jedrzej.backend.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.warczynski.jedrzej.backend.dto.RegistrationDto;
import pl.warczynski.jedrzej.backend.services.interfaces.EmailService;
import org.springframework.mail.SimpleMailMessage;
import java.security.SecureRandom;
import java.util.Base64;

import static pl.warczynski.jedrzej.backend.common.Constants.BASE_URL;
import static pl.warczynski.jedrzej.backend.common.Constants.VERIFICATION_EMAIL_CONTENT;
import static pl.warczynski.jedrzej.backend.rest.RegistrationController.logger;


@Service
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender mailSender;
  private final SecureRandom secureRandom;

  @Autowired
  public EmailServiceImpl(JavaMailSender mailSender, SecureRandom secureRandom) {
    this.mailSender = mailSender;
    this.secureRandom = secureRandom;
  }

//  @Override
//  public void sendVerificationEmail(RegistrationDto registrationDto) {
//    SimpleMailMessage message = new SimpleMailMessage();
//    message.setFrom(PORTAL_EMAIL);
//    message.setTo(registrationDto.email());
//    message.setSubject(VERIFICATION_SUBJECT);
////    message.setText(VERIFICATION_EMAIL_CONTENT.getValue() + BASE_URL.getValue() + "/register?token=" + generateRandomToken());
//    message.setText(VERIFICATION_EMAIL_CONTENT.getValue() + "<a href=\"" + BASE_URL.getValue() + "/register?token=" + generateRandomToken() + "\">Click here to verify your email</a>");
//
//    mailSender.send(message);
//  }

  @Override
  public void sendVerificationEmail(RegistrationDto registrationDto) {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(PORTAL_EMAIL);
      helper.setTo(registrationDto.email());
      helper.setSubject(VERIFICATION_SUBJECT);
      String verificationLink = BASE_URL.getValue() + "/register?token=" + generateRandomToken();
      String htmlContent = "<html><body><p>"
        + VERIFICATION_EMAIL_CONTENT.getValue()
        + "<br><a href=\"" + verificationLink + "\">Click here to verify your account</a> (" + verificationLink + ")</p></body></html>";
      helper.setText(htmlContent, true);
      mailSender.send(message);
    } catch (MessagingException e) {
      logger.error("Error sending verification email", e);
    }
  }

  public void sendConfirmationEmail(String email) {
    email = "jedrasowicz@gmail.com";
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(PORTAL_EMAIL);
    message.setTo(email);
    message.setSubject(CONFIRMATION_SUBJECT);
    message.setText(CONFIRMATION_CONTENT);

    mailSender.send(message);
  }

  private String generateRandomToken() {
    byte[] tokenBytes = new byte[16];
    secureRandom.nextBytes(tokenBytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
  }
}
