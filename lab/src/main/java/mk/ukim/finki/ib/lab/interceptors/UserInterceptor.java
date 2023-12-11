package mk.ukim.finki.ib.lab.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.ib.lab.models.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = (User) request.getSession().getAttribute("user");

        if(user != null && modelAndView != null){
            modelAndView.getModel().put("user", user);
        }

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
