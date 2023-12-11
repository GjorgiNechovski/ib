package mk.ukim.finki.ib.lab.controllers;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.ib.lab.models.Product;
import mk.ukim.finki.ib.lab.models.enums.UserRole;
import mk.ukim.finki.ib.lab.services.interfaces.IProductService;
import mk.ukim.finki.ib.lab.services.interfaces.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import mk.ukim.finki.ib.lab.models.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EverythingController {

    private final IUserService userService;
    private final IProductService productService;

    public EverythingController(IUserService userService, IProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("main")
    public String getWelcomePage(@RequestParam(required = false) String error,
                                 Model model,
                                 HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        List<Product> products = this.productService.findAll();

        if (user != null) {
            model.addAttribute("user", user);
        }

        model.addAttribute("error", error);
        model.addAttribute("template", "main");
        model.addAttribute("products", products);

        return "nav-menu";
    }


    @GetMapping("userTable")
    public String getChangeRoles(Model model){
        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("template", "userTable");

        return "nav-menu";
    }

    @PostMapping("deleteUser")
    public String deleteUser(@RequestParam Integer userId,
                             HttpSession session,
                             Model model){

        User sessionUser = (User) session.getAttribute("user");
        User user = userService.findById(userId);

        if (sessionUser != null && user != null) {

            if (sessionUser.getUsername().equals(user.getUsername())) {
                model.addAttribute("error", "You can't delete yourself!");
                return "redirect:/userTable";
            }
        }

        this.userService.deleteById(userId);

        return "redirect:/userTable";
    }

    @GetMapping("changeRole")
    public String getChangeRole(@RequestParam Integer userId, Model model){
        User user = userService.findById(userId);

        model.addAttribute("user", user);
        model.addAttribute("template", "changeRoles");

        return "nav-menu";
    }

    @PostMapping("changeRole")
    public String changeUserRole(@RequestParam UserRole role,
                                 @RequestParam int id,
                                 HttpSession session,
                                 Model model){
        User sessionUser = (User) session.getAttribute("user");
        User user = userService.findById(id);

        if (sessionUser != null && user != null) {

            if (sessionUser.getUsername().equals(user.getUsername())) {
                model.addAttribute("error", "You can't change your own role!");
                return "changeRoles";
            }
        }

        userService.changeRole(id, role);

        return "redirect:/userTable";
    }

    @GetMapping("/addProduct")
    public String getAddProduct(Model model){
        model.addAttribute("template", "addProduct");

        return "nav-menu";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam String name,
                             @RequestParam int price,
                             HttpSession session){
        User user = (User) session.getAttribute("user");
        this.productService.addProduct(name, price, user);

        return "redirect:/main";
    }

    @GetMapping("/contacts")
    public String getContactPage(Model model){
        model.addAttribute("template", "contact");

        return "nav-menu";
    }
}
