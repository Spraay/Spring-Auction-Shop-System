package krystianrytel.sklepaukcyjny.services;


import javafx.util.Pair;
import krystianrytel.sklepaukcyjny.models.Cart;
import krystianrytel.sklepaukcyjny.models.Item;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CartService {
    Cart getCartById(long id);
    List<Cart> getUserCarts(String username);
    void addItemToCart(Long ItemId, String username, HttpSession session);
    void deleteCart(long cart_id);
    Float getCartPrice(long cart_id);
    Float getCartsPrice(String username);
    List<Cart> getCartsConsistsItemByItemId(long item_id);
    boolean isCartExists(long cart_id);
}
