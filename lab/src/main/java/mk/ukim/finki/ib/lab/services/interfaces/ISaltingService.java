package mk.ukim.finki.ib.lab.services.interfaces;

public interface ISaltingService {
    boolean validatePassword(String inputPassword, String hashedPassword, String salt);
    String generateSalt();
    String hashPassword(String password, String salt);
}
