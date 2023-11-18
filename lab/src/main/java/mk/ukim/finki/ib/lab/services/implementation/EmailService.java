package mk.ukim.finki.ib.lab.services.implementation;

import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import mk.ukim.finki.ib.lab.services.interfaces.IEmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @SneakyThrows
    @Override
    public void sendConfirmationEmail(String email, String token){
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String confirmationLink = "http://localhost:8080/confirm?token=" + token;

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

}
