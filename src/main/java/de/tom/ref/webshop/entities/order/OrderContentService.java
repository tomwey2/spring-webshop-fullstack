package de.tom.ref.webshop.entities.order;

import de.tom.ref.webshop.entities.carts.Cart;
import de.tom.ref.webshop.entities.carts.CartContent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OrderContentService {
    private final OrderContentRepository orderContentRepository;

    /**
     * Calculate the subtotal price of the products in the cart.
     * @param cart
     * @return
     */
    public BigDecimal calculateSubtotalSum(Order order) {
        List<OrderContent> orderContents = orderContentRepository.findByOrderId(order.getId());
        BigDecimal sum = BigDecimal.ZERO;
        for (OrderContent orderContent : orderContents) {
            sum = sum.add(orderContent.getPrice());
        }
        return sum;
    }

    public BigDecimal calculateShippingCosts(Order order) {
        return new BigDecimal("6.50");
    }

    public BigDecimal calculateTotalSum(Order order) {
        BigDecimal subTotalSum = calculateSubtotalSum(order);
        BigDecimal shippingCosts = calculateShippingCosts(order);
        return subTotalSum.add(shippingCosts);
    }

}
