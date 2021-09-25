package de.tom.ref.webshop.entities.carts;

import de.tom.ref.webshop.entities.customers.Customer;
import de.tom.ref.webshop.entities.customers.CustomerService;
import de.tom.ref.webshop.entities.products.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final CartContentRepository cartContentRepository;
    private final CartContentService cartContentService;
    private final CustomerService customerService;
    private final ProductService productService;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
    public void deleteCartById(Integer id) {
        cartRepository.deleteById(id);
    }

    /**
     * Get the shopping cart of a customer. If it not exists then create it.
     * @param customer the logged-in user
     * @return the cart of the customer.
     */
    public Cart getCartOfCustomer(Customer customer) {
        if (customer == null || StringUtils.isEmpty(customer.getEmail())) {
            return null;
        }
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

    /**
     * Get the content of a shopping cart.
     * @param cart the cart of the logged-in user.
     * @return all cart contents of the cart.
     */
    public List<CartContent> getCartContents(Cart cart) {
        return cartContentRepository.findByCartId(cart.getId());
    }

    private CartContent getCartContentById(Cart cart, Integer cartContentId) {
        List<CartContent> cartContents = getCartContents(cart);
        return cartContents.stream()
                .filter(cartContent -> cartContent.getId() == cartContentId)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public CartContent changeQuantityOfCartContent(Cart cart, Integer cartContentId, int value) {
        CartContent cartContent = getCartContentById(cart, cartContentId);
        return value > 0 ?
            cartContentService.increaseQuantity(cartContent, value)
            :
            cartContentService.decreaseQuantity(cartContent, Math.abs(value));
    }

    public void deleteProductFromCart(Cart cart, Integer cartContentId) {
        CartContent cartContent = getCartContentById(cart, cartContentId);
        cartContentService.deleteProductFromCart(cartContent);
    }

    public Cart emptyCart(Cart cart) {
        List<CartContent> cartContents = getCartContents(cart);
        for (CartContent cartContent : cartContents) {
            deleteProductFromCart(cart, cartContent.getId());
        }
        return cart;
    }

    public void deleteCartContent(Cart cart) {
        List<CartContent> cartContents = getCartContents(cart);
        cartContentRepository.deleteAll(cartContents);
    }
}
