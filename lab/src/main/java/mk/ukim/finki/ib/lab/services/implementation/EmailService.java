package mk.ukim.finki.ib.lab.services.implementation;

import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import mk.ukim.finki.ib.lab.services.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    private final JavaMailSender emailSender;
    @Value("${app.base-url}")
    private String baseUrl;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @SneakyThrows
    @Override
    public void sendConfirmationEmail(String email, String token){
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String confirmationLink = baseUrl + "/confirm?token=" + token;

        String emailContent = "<p>Click the following link to confirm your email: <a href=\"" +
                confirmationLink + "\">Confirm Email here</a></p>";

        try {
            helper.setTo(email);
            helper.setSubject("Email Confirmation");
            helper.setText(emailContent, true);

            emailSender.send(message);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public void sendChangePasswordEmail(String email, String token) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String confirmationLink = baseUrl + "/changePassword?token=" + token+"&email=" + email;

        String emailContent = "<p>Click the following link to confirm your email: <a href=\"" +
                confirmationLink + "\">Confirm Email here</a></p>";

        try {
            helper.setTo(email);
            helper.setSubject("Password Change validation");
            helper.setText(emailContent, true);

            emailSender.send(message);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public void loginConfirmationEmail(String email, int code) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String emailContent = "<p>Your code to finish your log in is: " + code + "</p>";

        try {
            helper.setTo(email);
            helper.setSubject("Two factor authorization");
            helper.setText(emailContent, true);

            emailSender.send(message);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
