package de.tom.ref.webshop.entities.carts;

import de.tom.ref.webshop.entities.customers.Customer;
import de.tom.ref.webshop.entities.customers.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/carts")
@AllArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    private final CustomerService customerService;

    @GetMapping("")
    public List<Cart> getAll() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{username}")
    public Cart getCartByUsername(@PathVariable String username) {
        log.info("Get cart of user={}", username);
        Customer customer = customerService.getCustomer(username);
        return cartService.getCartOfCustomer(customer);
    }

    @GetMapping("/{username}/size")
    public int getCartSize(@PathVariable String username) {
        log.info("Get amount of content in cart of user={}", username);
        Customer customer = customerService.getCustomer(username);
        return cartService.getAmountOfProductsInCart(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        log.info("Delete cart id={}", id);
        cartService.deleteCartById(id);
    }

}
