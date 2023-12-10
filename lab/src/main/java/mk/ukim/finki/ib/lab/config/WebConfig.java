package mk.ukim.finki.ib.lab.config;

import mk.ukim.finki.ib.lab.interceptors.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/*")
                .excludePathPatterns(
                        "/login",
                        "/register",
                        "/sendEmail",
                        "/changePassword",
                        "/confirm",
                        "/showAuthMessage",
                        "/codeConfirm"
                );
    }
}
