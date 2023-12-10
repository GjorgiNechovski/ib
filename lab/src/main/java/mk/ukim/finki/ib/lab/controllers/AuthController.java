package mk.ukim.finki.ib.lab.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.ib.lab.models.exceptions.*;
import mk.ukim.finki.ib.lab.services.implementation.CookieService;
import mk.ukim.finki.ib.lab.services.interfaces.IAuthService;
import mk.ukim.finki.ib.lab.services.interfaces.ICookieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import mk.ukim.finki.ib.lab.services.implementation.AuthService;
import mk.ukim.finki.ib.lab.models.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private final IAuthService authService;
    private final ICookieService cookieService;

    public AuthController(AuthService authService, CookieService cookieService) {
        this.authService = authService;
        this.cookieService = cookieService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String hashedPassword,
                        Model model){
        User user;

        try{
            user = authService.loginUser(email, hashedPassword);
        }
        catch (UserNotActiveException e){
            model.addAttribute("error", e.getMessage());
            return "/login";
        }

        if(user == null){
            model.addAttribute("error", "You have entered wrong credentials or the user doesn't exist!");
            return "/login";
        }

        return "redirect:/codeConfirm?email=" + email;
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String name,
                           @RequestParam String lastName,
                           @RequestParam String email,
                           @RequestParam String hashedPassword,
                           @RequestParam String hashedRepeatPassword,
                           Model model){
        try{
             authService.createUser(username, name, lastName, email, hashedPassword, hashedRepeatPassword);
        }
        catch (PasswordsDontMatchException | UserNameExistsException | EmailTakenException e){
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        model.addAttribute("message", "A confirmation link has been sent to your email account");

        return "showAuthMessage";
    }

    @GetMapping("/sendEmail")
    public String getSendEmailPage(){
        return "sendEmail";
    }

    @PostMapping("/sendEmail")
    public String checkEmail(@RequestParam String email,
                             Model model){
        try{
            authService.checkEmailExistence(email);
        } catch (EmailNotExistentException e) {
            model.addAttribute("error", e.getMessage());

            return "sendEmail";
        }

        authService.sendChangePasswordEmail(email);

        model.addAttribute("message", "An email has been sent to you to confirm your password change");

        return "showAuthMessage";
    }

    @GetMapping("/changePassword")
    public String getChangePasswordPage(@RequestParam String token, @RequestParam String email, Model model){
        if (email==null){
            EmailNotExistentException exception = new EmailNotExistentException();
            model.addAttribute("error", exception.getMessage());

            return "sendEmail";
        }

        try {
            authService.confirmChangePasswordToken(email,token);
        } catch (UserNotExistentException | TokensDoNotMatchException e) {
            model.addAttribute("message", e.getMessage());

            return "showAuthMessage";
        }

        model.addAttribute("email", email);

        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String email,
                                 @RequestParam String hashedPassword,
                                 @RequestParam String hashedRepeatPassword,
                                 Model model){
        try {
            authService.changePassword(email,hashedPassword,hashedRepeatPassword);
        }
        catch (PasswordsDontMatchException e){
            model.addAttribute("error", model);

            return "changePassword";
        }
        model.addAttribute("message", "An authentication email has been sent to you account to change your password");

        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session,
                         HttpServletRequest request,
                         HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        Cookie token = cookieService.removeTokenCookie(cookies);
        response.addCookie(token);


        session.removeAttribute("token");
        return "/login";
    }

    @GetMapping("/confirm")
    public String confirmRegisterToken(@RequestParam String token){
        authService.confirmRegisterToken(token);

        return "redirect:/login";
    }

    @GetMapping("/codeConfirm")
    public String getConfirmLogInPage(@RequestParam String email,
                                      @RequestParam (required = false) String error,
                                      Model model){
        model.addAttribute("email",email);
        model.addAttribute("error", error);

        return "logCodeConfirm";
    }

    @PostMapping("/codeConfirm")
    public String confirmCode(@RequestParam String email,
                              @RequestParam int code,
                              HttpSession httpSession,
                              HttpServletResponse response,
                              RedirectAttributes redirectAttributes){
        User user;
        user = authService.getUserByEmail(email);

        try{
            authService.checkUserCode(user, code);
        }
        catch (AuthCodeNotMatchingException e){
            redirectAttributes.addAttribute("error", e.getMessage());

            return "redirect:/codeConfirm?email=" + email;
        }

        Cookie token = cookieService.generateTokenCookie();
        response.addCookie(token);

        httpSession.setAttribute("token", token.getValue());
        httpSession.setAttribute("user", user);

        return "redirect:/welcome";
    }

}
