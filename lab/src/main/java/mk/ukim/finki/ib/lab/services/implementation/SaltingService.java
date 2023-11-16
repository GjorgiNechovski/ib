package mk.ukim.finki.ib.lab.services.implementation;

import com.google.common.hash.Hashing;
import mk.ukim.finki.ib.lab.services.interfaces.ISaltingService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
public class SaltingService implements ISaltingService {
    public boolean validatePassword(String inputPassword, String hashedPassword, String salt) {
        String inputHashedPassword = hashPassword(inputPassword, salt);

        return hashedPassword.equals(inputHashedPassword);
    }

    public String generateSalt() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public String hashPassword(String password, String salt) {
        String saltedPassword = password + salt;

        return Hashing.sha256()
                .hashString(saltedPassword, StandardCharsets.UTF_8)
                .toString();
    }

    public String extractSalt(String hashedPassword) {
        int saltLength = 16;
        return hashedPassword.substring(0, saltLength);
    }
}
