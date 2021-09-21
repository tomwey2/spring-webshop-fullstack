package de.tom.ref.webshop.entities.customers;

import de.tom.ref.webshop.enums.UserRole;
import de.tom.ref.webshop.errorhandling.CustomerNotFoundException;
import de.tom.ref.webshop.errorhandling.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CustomerService {
    private final static String USER_NOT_FOUND = "User not found in the database: %s";

    private final CustomerRepository customerRepository;

    public Customer getCustomer(String username) {
        return customerRepository.findByEmail(username)
                .orElseThrow( () ->
                        new NotFoundException(
                                String.format(USER_NOT_FOUND, username))
                );
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getById(Integer id) {
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public Customer save(Customer customer) {
        log.info("Save new user {} to the database", customer.getEmail());
        return customerRepository.save(customer);
    }

    public Customer getByEmail(String email) {
        return customerRepository
                .findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(email));
    }

    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Customer customer) {
        UserRole role = customer.getUserRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    public void enableCustomer(Customer customer) {
        customer.setEnabled(true);
        save(customer);
    }

    public Customer getSignInCustomer() {
        Customer customer = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            customer = getCustomer(username);
        } catch (Exception e) {
            customer = new Customer();
        }
        ;
        return customer;
    }

}
