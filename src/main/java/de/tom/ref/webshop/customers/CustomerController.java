package de.tom.ref.webshop.customers;

import de.tom.ref.webshop.errorhandling.CustomerNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("")
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable Integer id) {
        return customerService.getById(id);
    }

    @GetMapping("/email/{email}")
    public Customer getByEmail(@PathVariable String email) {
        return customerService.getByEmail(email);
    }

    @PostMapping("/save")
    public Customer post(@RequestBody Customer customer) {
        log.debug("Call POST /api/customers/save {}", customer);
        return customerService.save(customer);
    }

    @PutMapping("/{id}")
    public Customer put(@RequestBody Customer object, @PathVariable Integer id) {
        Customer customer = customerService.getById(id);
        customer.setFirstName(object.getFirstName());
        customer.setLastName(object.getLastName());
        // ... TODO
        return customerService.save(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        customerService.deleteById(id);
    }

}
