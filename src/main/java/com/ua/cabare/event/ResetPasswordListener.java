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
public class ResetPasswordListener {

  @Autowired
  private VerificationTokenService verificationTokenService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private JavaMailSender mailSender;

  @Async
  @EventListener
  public void handleResetPassword(ResetPasswordEvent event) {
    Employee employee = event.getEmployee();
    String token = verificationTokenService.getToken();
      verificationTokenService.createVerificationTokenForEmployee(employee, token);

    SimpleMailMessage email = createEmailMessage(event, employee);
    mailSender.send(email);
  }


  private SimpleMailMessage createEmailMessage(ResetPasswordEvent event, Employee employee) {
    String emailAddress = employee.getEmail();
    String subject = "Reset password";
    String confirmationUrl = event.getAppUrl() + "/password/update/" + employee.getId();
    String message = messageSource.getMessage("message.resetPassword", null, event.getLocale());
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(emailAddress);
    simpleMailMessage.setSubject(subject);
    simpleMailMessage.setText(message + "\r\n" + confirmationUrl);
    simpleMailMessage.setFrom("spring.mail.username");
    return simpleMailMessage;
  }

}
