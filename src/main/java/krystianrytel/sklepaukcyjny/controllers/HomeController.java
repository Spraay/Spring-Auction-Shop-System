package krystianrytel.sklepaukcyjny.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/home", "/"})
public class HomeController {

    private static final Logger logger = LogManager.getLogger(HomeController.class);

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/item/list";
    }

    @GetMapping("/about")
    public String about(Model model){
        return "home";
    }

    @GetMapping("/contact")
    public String contact(Model model){
        return "home";
    }

}



