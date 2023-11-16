package mk.ukim.finki.ib.lab.interceptors;

import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String token = (String) request.getSession().getAttribute("token");

        if(cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getValue());
                if (cookie.getValue().equals(token))
                    return true;
            }
        }

        response.sendRedirect("/login");
        return false;

    }
}
