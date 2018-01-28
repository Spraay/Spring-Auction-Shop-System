package krystianrytel.sklepaukcyjny.services;

import krystianrytel.sklepaukcyjny.models.*;
import krystianrytel.sklepaukcyjny.repositories.AuctionRepository;
import krystianrytel.sklepaukcyjny.repositories.CartRepository;
import krystianrytel.sklepaukcyjny.repositories.OrderRepository;
import krystianrytel.sklepaukcyjny.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Override
    public void saveUserOrder(List<Cart> carts) {
        carts.forEach(cart->{
            User user = userRepository.findByUsername(cart.getUsername());
            String item_name = cart.getItem().getName();
            Float item_price = cart.getItem().getPrice();
            String item_description = cart.getItem().getDescription();
            Category category = cart.getItem().getCategory();
            //TheOrder(User user, String item_name, float item_price, String item_description, @Valid Category category, Date createdDate)
            orderRepository.save(new TheOrder(user, item_name, item_price, item_description, category, new Date() ));
            cartRepository.deleteById(cart.getId());
        });
    }

    @Override
    public List<TheOrder> getUserOrders(String username) {
        return orderRepository.getUserOrders(username);
    }

    @Override
    public Float getOrdersPrice(List<TheOrder> orders) {
        AtomicReference<Float> price= new AtomicReference<>(0f);
        orders.forEach(order-> price.updateAndGet(v -> v + order.getItem_price()));
        return price.get();
    }

    @Override
    public void setUpWinningAuction(Auction auction) {
        TheOrder neworder = new TheOrder();
        neworder.setCategory(auction.getItem().getCategory());
        neworder.setCreatedDate(new Date());
        neworder.setItem_description(auction.getItem().getDescription());
        neworder.setItem_name(auction.getItem().getName());
        neworder.setItem_price(auction.getItem().getPrice());
        neworder.setUser(auction.getUser());
        neworder.setUser_name(auction.getUser().getUsername());
        orderRepository.save(neworder);
    }
}
