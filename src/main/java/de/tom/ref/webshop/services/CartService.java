package de.tom.ref.webshop.services;

import de.tom.ref.webshop.entities.Cart;
import de.tom.ref.webshop.entities.CartContent;
import de.tom.ref.webshop.entities.Customer;
import de.tom.ref.webshop.repositories.CartContentRepository;
import de.tom.ref.webshop.repositories.CartRepository;
import de.tom.ref.webshop.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartContentRepository cartContentRepo;
    private final CustomerRepository customerRepo;
    private final CartRepository cartRepo;

    public CartService(CartContentRepository cartContentRepo, CustomerRepository customerRepo, CartRepository cartRepo) {
        this.cartContentRepo = cartContentRepo;
        this.customerRepo = customerRepo;
        this.cartRepo = cartRepo;
    }

    public CartContent addToCart(Integer cartId, Integer productId) {
        CartContent content = new CartContent(cartId, productId, 1);
        return cartContentRepo.save(content);
    }

    public int getAmountOfProductsInCart(Integer cartId) {
        List<CartContent> contents = cartContentRepo.findByCartId(cartId);
        return contents.size();
    }

    public Cart createCartForCustomer(Integer customerId) {
        Optional<Customer> customer = customerRepo.findById(customerId);
        Cart cart = customer.map(Cart::new).orElseThrow(IllegalStateException::new);
        return cartRepo.save(cart);
    }
}
