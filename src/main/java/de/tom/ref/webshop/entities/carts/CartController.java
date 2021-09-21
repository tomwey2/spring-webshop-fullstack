package de.tom.ref.webshop.entities.carts;

import de.tom.ref.webshop.entities.products.Product;
import de.tom.ref.webshop.errorhandling.CustomerNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/carts")
public class CartController {
    Logger log = LogManager.getLogger(CartController.class);

    @Autowired
    CartRepository repository;

    @GetMapping("")
    public List<Cart> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    Cart getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @GetMapping("/customer/{customer_id}")
    Cart getByCustomerId(@PathVariable Integer customerId) {
        return repository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @PostMapping("/save")
    String save(@RequestBody Cart newCart) {
        log.debug("Call POST /carts/save {}", newCart);
        //Cart cart = create(newCart.getCustomer());
        Cart savedCart = repository.save(newCart);
        return String.valueOf(savedCart.getId());
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
