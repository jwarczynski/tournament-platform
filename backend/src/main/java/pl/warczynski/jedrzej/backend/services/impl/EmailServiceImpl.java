package pl.warczynski.jedrzej.backend.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.warczynski.jedrzej.backend.dto.authentication.RegistrationDto;
import pl.warczynski.jedrzej.backend.services.interfaces.EmailService;
import org.springframework.mail.SimpleMailMessage;

import static pl.warczynski.jedrzej.backend.ApiUrls.VERIFICATION_EMAIL_URL;
import static pl.warczynski.jedrzej.backend.common.Constants.VERIFICATION_EMAIL_CONTENT;
import static pl.warczynski.jedrzej.backend.rest.UserRestEndpoint.logger;


@Service
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender mailSender;

  @Autowired
  public EmailServiceImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void sendVerificationEmail(RegistrationDto registrationDto, String token) {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(PORTAL_EMAIL);
      helper.setTo(registrationDto.email());
      helper.setSubject(VERIFICATION_SUBJECT);
      String verificationLink = VERIFICATION_EMAIL_URL + "?token=" + token;
      String htmlContent = "<html><body><p>"
              + VERIFICATION_EMAIL_CONTENT.getValue()
              + "<br><a href=\"" + verificationLink + "\">Click here to verify your account<br></a> (" + verificationLink + ")</p></body></html>";
      helper.setText(htmlContent, true);
      mailSender.send(message);
    } catch (MessagingException e) {
      logger.error("Error sending verification email", e);
    }
  }

    public void sendRestPasswordEmail(String email, String token) {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper;
      try {
        helper = new MimeMessageHelper(message, true);
        helper.setFrom(PORTAL_EMAIL);
        helper.setTo(email);
        helper.setSubject(RESET_PASSWORD_SUBJECT);
        String htmlContent =
                "<html><body><p>"
                + RESET_PASSWORD_CONTENT + token
                + "</p></body></html>";
        helper.setText(htmlContent, true);
        mailSender.send(message);
      } catch (MessagingException e) {
        logger.error("Error sending reset password email", e);
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

}
