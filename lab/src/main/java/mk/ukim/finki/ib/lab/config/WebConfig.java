package mk.ukim.finki.ib.lab.config;

import mk.ukim.finki.ib.lab.interceptors.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/*")
                .excludePathPatterns(authUrls);

        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/*")
                .excludePathPatterns(authUrls);

        registry.addInterceptor(new AdminPagesInterceptor())
                .addPathPatterns(
                        "/deleteUser",
                        "/userTable",
                        "/changeRole");

        registry.addInterceptor(new StudioPagesInterceptor())
                .addPathPatterns("/addProduct");
    }

    List<String> authUrls = List.of("/login", "/register", "/sendEmail", "/changePassword", "/confirm", "/showAuthMessage", "/codeConfirm");
}
