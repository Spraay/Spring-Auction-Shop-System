package krystianrytel.sklepaukcyjny.services;

import krystianrytel.sklepaukcyjny.models.Cart;
import krystianrytel.sklepaukcyjny.models.Item;
import krystianrytel.sklepaukcyjny.models.User;
import krystianrytel.sklepaukcyjny.repositories.CartRepository;
import krystianrytel.sklepaukcyjny.repositories.ItemRepository;
import krystianrytel.sklepaukcyjny.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Log4j2
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart getCartById(long id) {
        return cartRepository.getOne(id);
    }

    @Override
    public List<Cart> getUserCarts(String username)
    {
        return cartRepository.getUserCart(username);
    }


    @Override
    public void addItemToCart(Long ItemId, String username, HttpSession session){
        Item item = itemRepository.getOne(ItemId);
        if( item.getQuantity()>0 ){
            item.setQuantity(item.getQuantity()-1);
            User user = userRepository.findByUsername(username);
            Cart cart = new Cart();
            cart.setItem(item);
            cart.setUsername(username);
            cartRepository.save(cart);
            log.info("User '"+username+"' buy item '"+item.getName()+"'");
        }
    }

    @Override
    public void deleteCart(long cart_id) {
        cartRepository.deleteById(cart_id);
    }

    @Override
    public Float getCartPrice(long cart_id) {
        return cartRepository.getOne(cart_id).getItem().getPrice();
    }

    @Override
    public Float getCartsPrice(String username) {
        AtomicReference<Float> price = new AtomicReference<>((float) 0.00);
        getUserCarts(username).forEach(cart-> price.updateAndGet(v -> new Float((float) (v + cart.getItem().getPrice()))));
        return price.get();
    }

    @Override
    public List<Cart> getCartsConsistsItemByItemId(long item_id) {
        List<Cart> carts = new ArrayList<>();
        cartRepository.findAll().forEach(cart->{
            if(cart.getItem().getId()== item_id){
                carts.add(cart);
            }
        });
        return carts;
    }

    @Override
    public boolean isCartExists(long cart_id) {
        return cartRepository.existsById(cart_id);
    }
}
