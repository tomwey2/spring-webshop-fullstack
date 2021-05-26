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
    CustomerRepository repository;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/customers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Customer> getAll() {
        return repository.findAll();
    }

    @GetMapping("/customers/{id}")
    Customer getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @GetMapping("/customers/{name}")
    Customer getByUserName(@PathVariable String name) {
        return repository.findByUserName(name)
                .orElseThrow(() -> new CustomerNotFoundException(name));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/createCustomer",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Customer post(@RequestBody Customer newCustomer) {
        Customer customer = create(newCustomer.getUserName(), newCustomer.getFirstName(), newCustomer.getLastName(),
                newCustomer.getEmail(), newCustomer.getAddressLine1(), newCustomer.getAddressLine2(),
                newCustomer.getCity(), newCustomer.getPostalCode(), newCustomer.getCountry());
        log.debug("Call POST /createCustomer/{}", customer);
        return repository.save(customer);
    }

    @PutMapping("/customers/{id}")
    Customer put(@RequestBody Customer object, @PathVariable Integer id) {
        return repository.findById(id)
                .map(customer -> {
                    customer.setFirstName(object.getFirstName());
                    customer.setLastName(object.getLastName());
                    // ... TODO
                    return repository.save(customer);
                }).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @DeleteMapping("/customers/{id}")
    void deleteById(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    public Customer create(String userName, String firstName, String lastName, String email,
                           String addressLine1, String addressLine2,
                           String city, String postalcode, String country) {
        return new Customer(userName, firstName, lastName, email,
                addressLine1, addressLine2, city, postalcode, country);
    }

}
