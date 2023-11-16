package mk.ukim.finki.ib.lab.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import mk.ukim.finki.ib.lab.models.User;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {

    @GetMapping
    public String getWelcomePage(Model model, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");

        if(user!=null) {
            model.addAttribute("user", user);
        }

        return "welcome";
    }
}
