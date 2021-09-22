package de.tom.ref.webshop.entities.carts;

import de.tom.ref.webshop.Constants;
import de.tom.ref.webshop.entities.customers.Customer;
import de.tom.ref.webshop.entities.customers.CustomerRepository;
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
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;

    public List<Cart> getAll() {
        return cartRepository.findAll();
    }
    public void deleteById(Integer id) {
        cartRepository.deleteById(id);
    }

    public Cart getCartOfCustomer(String username) {
        Optional<Customer> customer = customerRepository.findByEmail(username);
        if (customer.isPresent()) {
            return cartRepository
                    .findByCustomerId(customer.get().getId())
                    .orElse(addNewCart(customer.get()));
        } else {
            throw new UsernameNotFoundException(
                    String.format(Constants.USER_NOT_FOUND, username));
        }
    }

    public Cart addNewCart(Customer customer) {
        Cart cart = new Cart(customer);
        return cartRepository.save(cart);
    }

    public CartContent addProductToCart(String username, Integer productId) {
        Cart cart = getCartOfCustomer(username);
        Product product = productService.getProduct(productId);
        CartContent content = new CartContent(cart, product, 1);
        return cartContentRepo.save(content);
    }

    public List<CartContent> getCartContent(Cart cart) {
        return cartContentRepo.findByCartId(cart.getId());
    }

    public int getAmountOfProductsInCart(String username) {
        int result = 0;
        if (!StringUtils.isEmpty(username)) {
            Cart cart = getCartOfCustomer(username);
            List<CartContent> contents = cartContentRepo.findByCartId(cart.getId());
            result = contents.size();
        }
        return result;
    }

}
