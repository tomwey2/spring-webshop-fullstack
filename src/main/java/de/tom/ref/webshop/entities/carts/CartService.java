package de.tom.ref.webshop.entities.carts;

import de.tom.ref.webshop.entities.customers.Customer;
import de.tom.ref.webshop.entities.customers.CustomerService;
import de.tom.ref.webshop.entities.products.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CartService {
    private final CartContentRepository cartContentRepository;
    private final CartRepository cartRepository;
    private final CustomerService customerService;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
    public void deleteCartById(Integer id) {
        cartRepository.deleteById(id);
    }

    /**
     * Get the shopping cart of a customer. If it not exists then create it.
     * @param customer
     * @return the cart of the customer.
     */
    public Cart getCartOfCustomer(Customer customer) {
        log.info("Get cart of user {} (id={})", customer.getEmail(), customer.getId());
        Optional<Cart> cart = cartRepository.findByCustomerId(customer.getId());
        if (cart.isPresent()) {
            return cart.get();
        } else {
            return addNewCart(customer);
        }
    }

    private Cart addNewCart(Customer customer) {
        log.info("Add new cart for user {} (id={})", customer.getEmail(), customer.getId());
        Cart cart = new Cart(customer);
        return cartRepository.save(cart);
    }

    public CartContent saveCartContent(CartContent cartContent) {
        return cartContentRepository.save(cartContent);
    }

    /**
     * Get the content of a shopping cart.
     * @param cart
     * @return
     */
    public List<CartContent> getCartContents(Cart cart) {
        return cartContentRepository.findByCartId(cart.getId());
    }

    /**
     * Add a product to a cart.
     * @param cart
     * @param product
     * @return
     */
    public CartContent addProductToCart(Cart cart, Product product) {
        log.info("Add product {} (id={}) to the cart id={}", product.getName(), product.getId(), cart.getId());
        CartContent cartContent = cartContentRepository.findByProductId(product.getId());
        if (cartContent != null) {
            log.info("Product id={} already in cart id={}", product.getId(), cart.getId());
            return cartContent;
        } else {
            cartContent = new CartContent(cart, product, 1);
            return cartContentRepository.save(cartContent);
        }
    }

    public void deleteProductFromCart(Cart cart, CartContent cartContent) {
        cartContentRepository.delete(cartContent);
    }

    public CartContent getCartContentById(Cart cart, Integer cartContentId) {
        List<CartContent> cartContents = getCartContents(cart);
        return cartContents.stream()
                .filter(cartContent -> cartContent.getId() == cartContentId)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Get the amount of products in a cart of a user.
     * @param customer
     * @return
     */
    public int getAmountOfProductsInCart(Customer customer) {
        int result = 0;
        if (customer != null && StringUtils.isNotEmpty(customer.getEmail())) {
            Cart cart = getCartOfCustomer(customer);
            List<CartContent> contents = cartContentRepository.findByCartId(cart.getId());
            for(CartContent cartContent : contents) {
                result += cartContent.getQuantity();
            }
        }
        return result;
    }

    /**
     * Calculate the subtotal price of the products in the cart.
     * @param cart
     * @return
     */
    public BigDecimal calculateSubtotalSum(Cart cart) {
        List<CartContent> cartContents = cartContentRepository.findByCartId(cart.getId());
        BigDecimal sum = BigDecimal.ZERO;
        for (CartContent cartContent : cartContents) {
            sum = sum.add(cartContent.getPrice());
        }
        return sum;
    }

    public BigDecimal calculateShippingCosts(Cart cart) {
        return new BigDecimal("6.50");
    }
    public BigDecimal calculateTotalSum(Cart cart) {
        BigDecimal subTotalSum = calculateSubtotalSum(cart);
        BigDecimal shippingCosts = calculateShippingCosts(cart);
        return subTotalSum.add(shippingCosts);
    }



}
