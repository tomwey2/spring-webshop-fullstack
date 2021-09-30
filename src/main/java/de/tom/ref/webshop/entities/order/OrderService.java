package de.tom.ref.webshop.entities.order;

import de.tom.ref.webshop.entities.carts.Cart;
import de.tom.ref.webshop.entities.carts.CartContent;
import de.tom.ref.webshop.entities.carts.CartContentService;
import de.tom.ref.webshop.entities.carts.CartService;
import de.tom.ref.webshop.entities.customers.Customer;
import de.tom.ref.webshop.registration.email.EmailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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
    private final EmailSender emailSender;
    private final CartContentService cartContentService;
    private final OrderContentService orderContentService;

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

        // create and send the confirmation email to the customer
        final String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        emailSender.send(
                customer.getEmail(),
                "Order Confirmation: " + order.getNumber(),
                buildEmail(
                        customer.getName(),
                        order,
                        orderContentRepository.findByOrderId(order.getId()),
                        orderContentService.calculateSubtotalSum(order),
                        orderContentService.calculateShippingCosts(order),
                        orderContentService.calculateTotalSum(order),
                        baseUrl)
        );

        return order;
    }

    private String buildEmail(String name, Order order,
                              List<OrderContent> orderContents,
                              BigDecimal subTotalSum,
                              BigDecimal shippingCosts,
                              BigDecimal totalSum,
                              String baseUrl) {
        // Table formatting in this email is adapted from w3schools.com, see
        // https://www.w3schools.com/html/html_tables.asp
        String emailText = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table {font-family: arial, sans-serif;border-collapse: collapse; width: 100%;}\n" +
                "td, th {text-align: left;padding: 8px;}\n" +
                "tr {border-bottom: 1px solid #ddd;}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <p style=\"color:Blue;\"><b>THIS EMAIL IS ONLY FOR TESTING PURPOSES TO " +
                "DEMONSTRATE THE FUNCTIONALITY OF MY TEST WEBSHOP.</b></p>" +
                "  <p>Hi " + name + ",</p>\n" +
                "  <p>Thank you for your order!<br>The content of your order is:</p>\n" +
                "  <h4>Order: " + order.getNumber() + "</h4>\n" +
                "  <table>\n" +
                "    <thead>\n" +
                "      <tr>\n" +
                "        <th>Product</th>\n" +
                "        <th style=\"text-align: right;\">Price (p. Unit)</th>\n" +
                "        <th style=\"text-align: center;\">Quantity</th>\n" +
                "        <th style=\"text-align: right;\">Price</th>\n" +
                "      </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n";

        for (OrderContent orderContent : orderContents) {
            emailText += "      <tr>\n" +
                    "        <td>" + orderContent.getProduct().getTitle() + "</td>\n" +
                    "        <td style=\"text-align: right;\">" + orderContent.getProduct().getPrice() + " Euro</td>\n" +
                    "        <td style=\"text-align: center;\">" + orderContent.getQuantity() + "</td>\n" +
                    "        <td style=\"text-align: right;\">" + orderContent.getPrice() + " Euro</td>\n" +
                    "      </tr>\n";
        }
        emailText += "    <tr>\n" +
                "        <td><b>Subtotal</b></td>\n" +
                "        <td/>\n" +
                "        <td/>\n" +
                "        <td style=\"text-align: right;\"><b>" + subTotalSum + " Euro</b></td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td>Shipping Costs</td>\n" +
                "        <td/>\n" +
                "        <td/>\n" +
                "        <td style=\"text-align: right;\">" + shippingCosts + " Euro</td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td><b>Total to pay</b></td>\n" +
                "        <td/>\n" +
                "        <td/>\n" +
                "        <td style=\"text-align: right;\"><b>" + totalSum + " Euro</b></td>\n" +
                "      </tr>\n" +
                "    </tbody>\n" +
                "  </table>\n" +
                "  <p>Regards,<br>Thomas</p>\n" +
                "  <p><a href=\"" + baseUrl + "\">Visit the webshop</a></p>\n" +
                "</body>\n" +
                "</html>\n";

        return emailText;
    }

}
