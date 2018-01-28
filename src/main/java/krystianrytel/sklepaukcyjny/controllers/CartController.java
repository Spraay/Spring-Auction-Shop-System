package krystianrytel.sklepaukcyjny.controllers;


import krystianrytel.sklepaukcyjny.models.Cart;
import krystianrytel.sklepaukcyjny.models.Item;
import krystianrytel.sklepaukcyjny.services.CartService;
import krystianrytel.sklepaukcyjny.services.ItemService;
import krystianrytel.sklepaukcyjny.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Secured("ROLE_USER")
    @GetMapping("/add/{ItemId}")
    public String addItemToCartRequest(@PathVariable Long ItemId, RedirectAttributes ra, HttpSession session){
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        /*TODO
            1. Wyświetli widok w którym wyświetli dane przedmiotu, oraz przycisk kup bądź anuluj;
        */
        if(!itemService.getItem(ItemId).isIsauction()){
            ra.addFlashAttribute("message", "<div class='green-text'>Dodano przedmiot do koszyka!</div>");
            cartService.addItemToCart(ItemId, currentPrincipalName, session);
        }else{
            ra.addFlashAttribute("message", "<div class='orange-text'>Podany przedmiot jest licytacją i nie można go kupić!</div>");
        }
        session.removeAttribute("cartlist");
        session.setAttribute("cartlist", cartService.getUserCarts(currentPrincipalName));
        return "redirect:/item/list";
    }

    @Secured("ROLE_USER")
    @GetMapping("/delete/{id}")
    public String deleteItemFromCart(@PathVariable long id, RedirectAttributes ra, HttpSession session){
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        if( cartService.isCartExists(id) ){
            Item item = itemService.getItem(cartService.getCartById(id).getItem().getId());
            item.setQuantity(item.getQuantity()+1);
            cartService.deleteCart(id);
            ra.addFlashAttribute("message", "<div class='yellow-text'>Usunięto przedmiot z koszyka!</div>");
        }else{
            ra.addFlashAttribute("message", "<div class='red-text'>Nie posiadasz tego przedmiotu w koszyku!</div>");
        }
        session.setAttribute("cartlist", cartService.getUserCarts(currentPrincipalName));
        return "redirect:/cart/list";
    }

    @Secured("ROLE_USER")
    @GetMapping("/list")
    public String showUserCart(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Cart> userCart = cartService.getUserCarts(username);
        model.addAttribute("userCart", userCart);
        model.addAttribute("itemsPrice", cartService.getCartsPrice(username));
        return "/cart/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder2) {//Rejestrujemy edytory właściwości
        DecimalFormat numberFormat = new DecimalFormat("#0.00");
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
        CustomNumberEditor priceEditor = new CustomNumberEditor(Float.class, numberFormat, true);
        binder2.registerCustomEditor(Float.class, "itemsPrice", priceEditor);
        binder2.registerCustomEditor(Float.class, "cart.item.price", priceEditor);
    }
}
