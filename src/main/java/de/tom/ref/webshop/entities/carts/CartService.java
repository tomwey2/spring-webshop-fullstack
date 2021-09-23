package de.tom.ref.webshop.entities.carts;

import de.tom.ref.webshop.Constants;
import de.tom.ref.webshop.entities.customers.Customer;
import de.tom.ref.webshop.entities.customers.CustomerRepository;
import de.tom.ref.webshop.entities.customers.CustomerService;
import de.tom.ref.webshop.entities.products.Product;
import de.tom.ref.webshop.entities.products.ProductService;
import de.tom.ref.webshop.errorhandling.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CartService {
    private final CartContentRepository cartContentRepo;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    public List<Cart> getAll() {
        return cartRepository.findAll();
    }
    public void deleteById(Integer id) {
        cartRepository.deleteById(id);
    }

    public Cart getCartOfCustomer(Customer customer) {
        log.info("Get cart of user {} (id={})", customer.getEmail(), customer.getId());
        Optional<Cart> cart = cartRepository.findByCustomerId(customer.getId());
        if (cart.isPresent()) {
            log.info("Cart found id={}", cart.get().getId());
            return cart.get();
        } else {
            return addNewCart(customer);
        }
    }

    public Cart addNewCart(Customer customer) {
        log.info("Add new cart for user {}", customer.getEmail());
        Cart cart = new Cart(customer);
        return cartRepository.save(cart);
    }

    public CartContent addProductToCart(Cart cart, Product product) {
        CartContent content = new CartContent(cart, product, 1);
        return cartContentRepo.save(content);
    }

    public List<CartContent> getCartContent(Cart cart) {
        return cartContentRepo.findByCartId(cart.getId());
    }

    public int getAmountOfProductsInCart(String username) {
        int result = 0;
        if (!StringUtils.isEmpty(username)) {
            Customer customer = customerService.getCustomer(username);
            Cart cart = getCartOfCustomer(customer);
            List<CartContent> contents = cartContentRepo.findByCartId(cart.getId());
            result = contents.size();
        }
        return result;
    }

}
