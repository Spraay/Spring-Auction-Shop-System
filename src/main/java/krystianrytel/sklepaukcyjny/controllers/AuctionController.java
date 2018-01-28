package krystianrytel.sklepaukcyjny.controllers;

import krystianrytel.sklepaukcyjny.controllers.commands.AuctionFilter;
import krystianrytel.sklepaukcyjny.controllers.commands.ItemFilter;
import krystianrytel.sklepaukcyjny.models.*;
import krystianrytel.sklepaukcyjny.services.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@SessionAttributes(names={"categories", "auction", "auctionCommand"})
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Secured("IS_AUTHENTICATED_FULLY")
    @GetMapping("/details/{id}")
    public String showItemDetails(Model model, @PathVariable("id") Long id){
        log.info("Pokazywanie szczegółów aukcji");
        model.addAttribute("auction", auctionService.getAuction(id));
        return "/auction/details";
    }

    @ModelAttribute("auctionCommand")
    public AuctionFilter getSimpleAuctionSearch(){
        return new AuctionFilter();
    }

    @Secured("IS_AUTHENTICATED_FULLY")
    @GetMapping("/list/showall")
    public String resetAuctionList(@ModelAttribute("auctionCommand") AuctionFilter search){
        search.clear();
        return "redirect:/auction/list";
    }

    @Secured("IS_AUTHENTICATED_FULLY")
    @RequestMapping(value="/list", method = {RequestMethod.GET, RequestMethod.POST} )
    public String showAuctionList(Model model, @ModelAttribute("auctionCommand") AuctionFilter search, Pageable pageable){
        model.addAttribute("auctionListPage", auctionService.getAllAuctions(search, pageable));
        return "/auction/list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{did}")
    public String deleteAuction(RedirectAttributes ra, @PathVariable long did, HttpSession session, HttpServletRequest request ){
        cartService.getCartsConsistsItemByItemId(auctionService.getAuction(did).getItem().getId()).forEach(cart->cartService.deleteCart(cart.getId()));
        itemService.deleteItem(auctionService.getAuction(did).getItem().getId());
        auctionService.deleteAuction(did);
        ra.addFlashAttribute("message", "<div class='red-text' >Usunięto Przedmiot</div>");
        session.removeAttribute("cartlit");
        session.setAttribute("cartlist", cartService.getUserCarts(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "redirect:/auction/list";
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

    @Secured("ROLE_USER")
    @GetMapping("/bid/{id}")
    public String showBidForm(@PathVariable Long id, Model model){
        model.addAttribute("auction", auctionService.getAuction(id));
        return "/auction/bidform";
    }

    @Transactional
    @Secured("ROLE_USER")
    @GetMapping("/bid/bidbidder/{id}")
    public String processBid(@PathVariable Long id, RedirectAttributes ra, HttpSession session){
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        Auction auction = auctionService.getAuction(id);
        Item item = auction.getItem();
        if( new Date().before(auction.getExpirationDate()) ) {
            if (user.getWallet() >= auction.getItem().getPrice() + auction.getMinbidrise()) {
                if (auction.getUser() != null) { // jeśli był offerant
                    auction.getUser().setWallet(auction.getUser().getWallet() + auction.getItem().getPrice());
                }
                auction.getItem().setPrice(auction.getItem().getPrice() + auction.getMinbidrise());
                user.setWallet(user.getWallet() - auction.getItem().getPrice());
                auction.setUser(user);
                ra.addFlashAttribute("message", "<div class='green-text' >Zalicytowałeś przedmiot!</div>");
            } else {
                ra.addFlashAttribute("message", "<div class='red-text' >Nie posiadasz wystarczających środków, aby zalicytować ten przedmiot!</div>");
            }
        }else{
            orderService.setUpWinningAuction( auction );
            ra.addFlashAttribute("message", "<div class='orange-text' >Aukcja zakończona!</div>");
        }
        session.setAttribute("user", user);
        session.setAttribute("cartlist", cartService.getUserCarts(user.getUsername()));
        return String.valueOf("redirect:/auction/details/"+id);

    }






    @InitBinder
    public void initBinder(WebDataBinder binder2) {//Rejestrujemy edytory właściwości

        DecimalFormat numberFormat2 = new DecimalFormat("#0.00");
        numberFormat2.setMaximumFractionDigits(2);
        numberFormat2.setMinimumFractionDigits(2);

        numberFormat2.setGroupingUsed(false);
        binder2.registerCustomEditor(Float.class, "price", new CustomNumberEditor(Float.class, numberFormat2, false));
        binder2.registerCustomEditor(Float.class, "price", new CustomNumberEditor(Float.class, numberFormat2, false));
        binder2.setDisallowedFields("createdDate");//ze względu na bezpieczeństwo aplikacji to pole nie może zo

    }
}
