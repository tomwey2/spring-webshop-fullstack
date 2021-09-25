package de.tom.ref.webshop.entities.order;

import de.tom.ref.webshop.entities.carts.Cart;
import de.tom.ref.webshop.entities.carts.CartContent;
import de.tom.ref.webshop.entities.carts.CartService;
import de.tom.ref.webshop.entities.customers.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderContentRepository orderContentRepository;
    private final CartService cartService;

    public Order createOrder(Customer customer) {
        String number = UUID.randomUUID().toString();
        Order order = new Order(customer, number);
        orderRepository.save(order);
        return order;
    }

    public Order createOrderFromCart(Customer customer, Cart cart) {
        Order order = createOrder(customer);
        List<CartContent> cartContents = cartService.getCartContents(cart);
        for (CartContent cartContent : cartContents) {
            OrderContent orderContent = new OrderContent(order,
                    cartContent.getProduct(), cartContent.getQuantity());
            orderContentRepository.save(orderContent);
        }
        cartService.deleteCartContent(cart);
        return order;
    }
}
