package mk.ukim.finki.ib.lab.services.interfaces;

import jakarta.servlet.http.Cookie;

public interface ICookieService {
    Cookie generateTokenCookie();
    Cookie removeTokenCookie(Cookie[] cookies);
}
