package mk.ukim.finki.ib.lab.services.interfaces;

public interface IEmailService {
    void sendConfirmationEmail(String email, String token);
    void sendChangePasswordEmail(String email, String token);
    void loginConfirmationEmail(String email, int code);
}
