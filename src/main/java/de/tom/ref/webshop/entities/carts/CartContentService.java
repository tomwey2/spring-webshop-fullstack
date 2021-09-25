package de.tom.ref.webshop.entities.carts;

import de.tom.ref.webshop.entities.customers.Customer;
import de.tom.ref.webshop.entities.products.Product;
import de.tom.ref.webshop.entities.products.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CartContentService {
    private final CartContentRepository cartContentRepository;
    private final ProductService productService;
    private final CartService cartService;

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
        CartContent cartContent = cartContentRepository.findByProductAndCartId(cart.getId(), product.getId());
        if (cartContent != null) {
            log.info("Product id={} already in cart id={}", product.getId(), cart.getId());
            return cartContent;
        } else {
            cartContent = new CartContent(cart, product, 1);
            productService.decreaseUnitsInStock(product, 1);
            return cartContentRepository.save(cartContent);
        }
    }

    public void deleteProductFromCart(Cart cart, CartContent cartContent) {
        productService.increaseUnitsInStock(cartContent.getProduct(), cartContent.getQuantity());
        cartContentRepository.delete(cartContent);
    }

    private CartContent updatePrice(CartContent cartContent) {
        BigDecimal price = cartContent.getProduct().getUnitPrice()
                .multiply(new BigDecimal(cartContent.getQuantity()));
        cartContent.setPrice(price);
        return cartContent;
    }

    public CartContent increaseQuantity(CartContent cartContent, int quantity) {
        if (quantity <= cartContent.getProduct().getUnitsInStock()) {
            productService.decreaseUnitsInStock(cartContent.getProduct(), quantity);
            cartContent.setQuantity(cartContent.getQuantity() + quantity);
            cartContent = updatePrice(cartContent);
            cartContent = cartContentRepository.save(cartContent);
        }
        return cartContent;
    }

    public CartContent decreaseQuantity(CartContent cartContent, int quantity) {
        if (quantity <= cartContent.getQuantity()) {
            cartContent.setQuantity(cartContent.getQuantity() - quantity);
            cartContent = updatePrice(cartContent);
            cartContent = cartContentRepository.save(cartContent);
            productService.increaseUnitsInStock(cartContent.getProduct(), quantity);
        }
        return cartContent;
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
    public int getAmountOfProductsInCart(Cart cart) {
        int result = 0;
        if (cart != null) {
            List<CartContent> contents = cartContentRepository.findByCartId(cart.getId());
            for (CartContent cartContent : contents) {
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
