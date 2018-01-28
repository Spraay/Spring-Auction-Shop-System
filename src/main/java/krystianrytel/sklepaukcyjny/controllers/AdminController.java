package krystianrytel.sklepaukcyjny.controllers;


import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@Secured("ADMIN")
public class AdminController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    String adminHome(){
        return "/admin/home";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/panel")
    public String showAdminPanel(Model model){
        return "/admin/panel";
    }


}
