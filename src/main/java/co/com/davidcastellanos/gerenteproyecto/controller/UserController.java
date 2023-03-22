package co.com.davidcastellanos.gerenteproyecto.controller;

import co.com.davidcastellanos.gerenteproyecto.model.LoginUser;
import co.com.davidcastellanos.gerenteproyecto.model.Project;
import co.com.davidcastellanos.gerenteproyecto.model.User;
import co.com.davidcastellanos.gerenteproyecto.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private AppService service;

    @GetMapping("/")
    public String index(@ModelAttribute("nuevoUser") User nuevoUser,
                        @ModelAttribute("nuevoLogin") LoginUser loginUser) {
        return "index.jsp";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("nuevoUser") User nuevoUser,
                               BindingResult result,
                               Model model,
                               HttpSession session) {
        service.registerUser(nuevoUser, result);

        if (result.hasErrors()) {
            model.addAttribute("nuevoLogin", new LoginUser());
            return "index.jsp";
        } else {
            session.setAttribute("userSession", nuevoUser);
            return "redirect:/dashboard";
        }

    }

    @GetMapping("/dashboard")
    public String dashBoard(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null) {
            return "redirect:/";
        }
       // proyectos a los que pertenece
        List<Project> myProjectsList = service.findMyProjects(currentUser);
        model.addAttribute("myProjectsList", myProjectsList);
        // proyectos a los que NO pertenece
        List<Project> otherProjectsList = service.finOtherProjects(currentUser);
        model.addAttribute("otherProjectsList", otherProjectsList);

        return "dashboard.jsp";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("nuevoLogin") LoginUser nuevoLogin,
                            BindingResult result,
                            Model model,
                            HttpSession session) {

        User user = service.loginUser(nuevoLogin, result);
        if (result.hasErrors()) {
            model.addAttribute("nuevoUser", new User());
            return "index.jsp";
        }

        session.setAttribute("userSession", user);
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logOutUser(HttpSession session) {
        User currentUser = (User) session.getAttribute("userSession");

        if (currentUser == null ) {
            return "redirect:/";
        } else {
            session.removeAttribute("userSession");
            return "redirect:/";
        }
    }


}
