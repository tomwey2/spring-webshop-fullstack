package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.Cart;
import de.tom.ref.webshop.entities.Customer;
import de.tom.ref.webshop.entities.Product;
import de.tom.ref.webshop.repositories.CartRepository;
import de.tom.ref.webshop.repositories.CustomerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    Logger log = LogManager.getLogger(CartController.class);

    @Autowired
    CartRepository repository;

    @GetMapping("/carts")
    public List<Cart> getAll() {
        return repository.findAll();
    }

    @GetMapping("/carts/{id}")
    Cart getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @GetMapping("/carts/customer/{customer_id}")
    Cart getByCustomerId(@PathVariable Integer customerId) {
        return repository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @PostMapping("/carts/save")
    String save(@RequestBody Cart newCart) {
        log.debug("Call POST /carts/save {}", newCart);
        //Cart cart = create(newCart.getCustomer());
        Cart savedCart = repository.save(newCart);
        return String.valueOf(savedCart.getId());
    }

    @PutMapping("/carts/{id}")
    Cart put(@RequestBody Product product, @PathVariable Integer id) {
        return repository.findById(id)
                /*
                .map(cart -> {
                    customer.setFirstName(object.getFirstName());
                    customer.setLastName(object.getLastName());
                    // ... TODO
                    return repository.save(customer);
                })
                 */
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @DeleteMapping("/carts/{id}")
    void deleteById(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    public Cart create(Customer customer) {
        return new Cart(customer);
    }

}
