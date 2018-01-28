package krystianrytel.sklepaukcyjny.controllers;

import krystianrytel.sklepaukcyjny.controllers.commands.ItemFilter;
import krystianrytel.sklepaukcyjny.models.Cart;
import krystianrytel.sklepaukcyjny.services.CartService;
import krystianrytel.sklepaukcyjny.services.ItemService;
import krystianrytel.sklepaukcyjny.models.Category;
import krystianrytel.sklepaukcyjny.models.Item;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@SessionAttributes(names={"categories", "item", "itemCommand"})
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    @Secured("IS_AUTHENTICATED_FULLY")
    @GetMapping("/details/{id}")
    public String showItemDetails(Model model, @PathVariable("id") Long id){
        log.info("Pokazywanie szczegółów przedmiotu");
        model.addAttribute("item", itemService.getItem(id));
        return "/item/details";
    }

    @ModelAttribute("itemCommand")
    public ItemFilter getSimpleItemSearch(){
        return new ItemFilter();
    }

    @Secured("IS_AUTHENTICATED_FULLY")
    @GetMapping("/list/showall")
    public String resetItemsList(@ModelAttribute("itemCommand") ItemFilter search){
        search.clear();
        return "redirect:/item/list";
    }

    @Secured("IS_AUTHENTICATED_FULLY")
    @RequestMapping(value="/list", method = {RequestMethod.GET, RequestMethod.POST} )
    public String showItemList(Model model, @ModelAttribute("itemCommand") ItemFilter search, Pageable pageable){
        model.addAttribute("itemListPage", itemService.getAllItems(search, pageable));
        return "/item/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{did}")
    public String deleteItem(RedirectAttributes ra, @PathVariable long did, HttpSession session, HttpServletRequest request ){
        cartService.getCartsConsistsItemByItemId(did).forEach(cart->cartService.deleteCart(cart.getId()));
        ra.addFlashAttribute("message", "<div class='red-text' >Usunięto Przedmiot</div>");
        session.removeAttribute("cartlit");
        session.setAttribute("cartlist", cartService.getUserCarts(SecurityContextHolder.getContext().getAuthentication().getName()));
        itemService.deleteItem(did);
        return "redirect:/item/list";
    }

    private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
        if (queryString.contains("&")) {
            return "?"+queryString.substring(queryString.indexOf("&") + 1);//obcinamy parametr did, bo inaczej po przekierowaniu znowu będzie wywołana metoda deleteVihicle
        }else{
            return "";
        }
    }

    @ModelAttribute("categories")
    public List<Category> loadCategories(){
        List<Category> categories = itemService.getAllTypes();
        log.info("Ładowanie listy "+categories.size()+" typów ");
        return categories;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping({"/add/{id}", "/add"})
    public String showForm(@PathVariable Optional<Long> id, Model model){
        model.addAttribute("item",
                id.isPresent()?
                        itemService.getItem(id.get()):
                        new Item());
        return "/item/add";
    }


    @Secured("ROLE_ADMIN")
    @PostMapping({"/add", "/add/{id}"})
    //@ResponseStatus(HttpStatus.CREATED)
    public String processForm(RedirectAttributes ra, @PathVariable Optional<Long> id, @Valid @ModelAttribute("item") Item v, BindingResult errors) {
        log.error("TEST: "+v.getId());
        if (errors.hasErrors()) {
            return "/item/add";
        }
        if( itemService.isExists(v) ){
            ra.addFlashAttribute("message", "<div class='blue-text'>Edytowano Przedmiot</div>");
            log.info("Data edycji komponentu "+new Date());
        }else{
            log.info("Data utworzenia komponentu "+v.getCreatedDate());
            ra.addFlashAttribute("message", "<div class='blue-text'>Dodano Przedmiot</div>");
        }
        itemService.saveItem(v);
        return "redirect:/item/list";//po udanym dodaniu/edycji przekierowujemy na listę
    }




    @InitBinder
    public void initBinder(WebDataBinder binder) {//Rejestrujemy edytory właściwości

        DecimalFormat numberFormat = new DecimalFormat("#0.00");
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
        binder.registerCustomEditor(Float.class, "price", new CustomNumberEditor(Float.class, numberFormat, false));
        binder.registerCustomEditor(Float.class, "price", new CustomNumberEditor(Float.class, numberFormat, false));
        binder.setDisallowedFields("createdDate");//ze względu na bezpieczeństwo aplikacji to pole nie może zostać przesłane w formularzu

    }

}
