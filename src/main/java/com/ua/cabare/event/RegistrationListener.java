package com.ua.cabare.event;

import com.ua.cabare.models.Employee;
import com.ua.cabare.services.VerificationTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener {

  @Autowired
  private VerificationTokenService verificationTokenService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private JavaMailSender mailSender;

  @Async
  @EventListener
  public void handleConfirmRegistration(ConfirmEmailEvent event) {
    Employee employee = event.getEmployee();
    String token;
    if (event.getExistingToken() == null) {
      token = verificationTokenService.getToken();
      verificationTokenService.createVerificationTokenForEmployee(employee, token);
    } else {
      token = event.getExistingToken().getToken();
    }

    SimpleMailMessage email = createEmailMessage(event, employee, token);
    mailSender.send(email);
  }


  private SimpleMailMessage createEmailMessage(ConfirmEmailEvent emailEvent, Employee employee,
      String token) {
    String emailAddress = employee.getEmail();
    String subject = "Registration confirmation";
    String confirmationUrl = emailEvent.getAppUrl() + "/registration/event?token=" + token;
    String message = messageSource.getMessage("message.emailText", null, emailEvent.getLocale());
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(emailAddress);
    simpleMailMessage.setSubject(subject);
    simpleMailMessage.setText(message + "\r\n" + confirmationUrl);
    simpleMailMessage.setFrom("spring.mail.username");
    return simpleMailMessage;
  }
}
