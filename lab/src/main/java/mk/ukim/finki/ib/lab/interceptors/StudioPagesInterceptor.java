package mk.ukim.finki.ib.lab.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.ib.lab.models.User;
import mk.ukim.finki.ib.lab.models.enums.UserRole;
import org.springframework.web.servlet.HandlerInterceptor;

public class StudioPagesInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserRole role = ((User) request.getSession().getAttribute("user")).getRole();

        if(role == UserRole.STUDIO){
            return true;
        }

        String errorMessage = "You are not authorized to access that page";
        response.sendRedirect("/main?error=" + errorMessage);
        return false;
    }

}