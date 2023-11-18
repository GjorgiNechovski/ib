package mk.ukim.finki.ib.lab.services.interfaces;

public interface IEmailService {
    void sendConfirmationEmail(String email, String token);
}
