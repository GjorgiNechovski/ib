package mk.ukim.finki.ib.lab.services.implementation;

import jakarta.servlet.http.Cookie;
import mk.ukim.finki.ib.lab.services.interfaces.ICookieService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CookieService implements ICookieService {
    @Override
    public Cookie generateTokenCookie() {
        String token = generateRandomString();
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(360);
        cookie.setPath("/");

        return cookie;
    }

    @Override
    public Cookie removeTokenCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("token")){
                cookie.setMaxAge(0);
                return cookie;
            }
        }
        return null;
    }

    private String generateRandomString(){
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
}
