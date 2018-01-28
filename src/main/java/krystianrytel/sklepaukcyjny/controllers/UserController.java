package krystianrytel.sklepaukcyjny.controllers;


import krystianrytel.sklepaukcyjny.models.Cart;
import krystianrytel.sklepaukcyjny.models.User;
import krystianrytel.sklepaukcyjny.services.CartService;
import krystianrytel.sklepaukcyjny.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
@Log4j2
@Controller
@RequestMapping("/user")
@SessionAttributes(names={"user"})
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userCommand", new User());
        return "/user/register";
    }

    @PostMapping("/register")
    public String registration(HttpSession session, Model model, @Valid @ModelAttribute("userCommand") User userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/user/register";
        }
        userService.save(userForm);
        model.addAttribute("message","<div class='green-text' >Zostałeś zarejestrowany pomyślnie, możesz się teraz zalogować!</div>");
        return "/user/login";
    }

    @GetMapping("/login")
    String showLoginForm(){
        return "/user/login";
    }

    @GetMapping("/logout")
    String logoutSuccess(Model model){
        model.addAttribute("message", "<div class='green-text' >Zostałeś wylogowany</div>");
        return "home";
    }

    @PostMapping("/login/success")
    String loginSuccess(Model model, HttpSession session){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        session.setAttribute("user", userService.getUser(username));
        session.setAttribute("cartlist", cartService.getUserCarts(username));
        model.addAttribute("message", "<div class='green-text' >Zostałeś zalogowany</div>");
        return "home";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/delete/{did}")
    public String deleteUser(RedirectAttributes ra, @PathVariable long did, HttpServletRequest request){
        userService.delete(did);
        ra.addFlashAttribute("message", "<div class='red-text' >Usunięto Użytkownika</div>");
        String queryString = prepareQueryString(request.getQueryString());
        return String.format("redirect:/user/list?%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu
    }

    private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
        return queryString.substring(queryString.indexOf("&")+1);//obcinamy parametr did, bo inaczej po przekierowaniu znowu będzie wywołana metoda deleteVihicle
    }



    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //aby użytkownik nie mógł sobie wstrzyknąć aktywacji konta oraz ról (np., ADMIN)
        //roles są na wszelki wypadek, bo warstwa serwisów i tak ustawia ROLE_USER dla nowego usera
        binder.setDisallowedFields("enabled", "roles");
    }

}

