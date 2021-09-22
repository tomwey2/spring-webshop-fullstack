package de.tom.ref.webshop.entities.carts;

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

    @GetMapping("")
    public List<Cart> getAll() {
        return cartService.getAll();
    }

    @GetMapping("/{username}")
    public Cart getCartByUsername(@PathVariable String username) {
        log.info("Get cart of user={}", username);
        return cartService.getCartOfCustomer(username);
    }

    @GetMapping("/{username}/size")
    public int getCartSize(@PathVariable String username) {
        log.info("Get amount of content in cart of user={}", username);
        return cartService.getAmountOfProductsInCart(username);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        log.info("Delete cart id={}", id);
        cartService.deleteById(id);
    }
}
