package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.Customer;
import de.tom.ref.webshop.repositories.CustomerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    Logger log = LogManager.getLogger(CustomerController.class);

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/customers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Customer> all() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    Customer one(@PathVariable Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(String.format("Could not find customer: %d", id)));
    }

    @PostMapping(
            value = "/createCustomer",
            consumes = "application/json",
            produces = "application/json")
    Customer createCustomer(@RequestBody Customer newCustomer) {
        log.debug("Call POST /createCustomer/{}", newCustomer);
        return customerRepository.save(newCustomer);
    }

    @PutMapping("/customers/{id}")
    Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Integer id) {

        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());
                    return customerRepository.save(customer);
                })
                .orElseGet(() -> {  // TODO
                    newCustomer.setId(id);
                    return customerRepository.save(newCustomer);
                });
    }

    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable Integer id) {
        customerRepository.deleteById(id);
    }

    public Customer createCustomer(String firstName, String lastName, String email,
                                   String addressLine1, String addressLine2,
                                   String city, String country) {
        return new Customer(firstName, lastName, email, addressLine1, addressLine2, city, country);
    }

}
