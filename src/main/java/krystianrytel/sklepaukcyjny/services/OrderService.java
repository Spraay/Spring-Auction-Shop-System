package krystianrytel.sklepaukcyjny.services;


import krystianrytel.sklepaukcyjny.models.Auction;
import krystianrytel.sklepaukcyjny.models.Cart;
import krystianrytel.sklepaukcyjny.models.TheOrder;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrderService {
    void saveUserOrder(List<Cart> carts);
    List<TheOrder> getUserOrders(String username);
    Float getOrdersPrice(List<TheOrder> orders);
    void setUpWinningAuction(Auction auction);
}
