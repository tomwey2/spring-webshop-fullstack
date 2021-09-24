package de.tom.ref.webshop.entities.customers;

import de.tom.ref.webshop.Constants;
import de.tom.ref.webshop.entities.carts.CartContent;
import de.tom.ref.webshop.enums.UserRole;
import de.tom.ref.webshop.errorhandling.CustomerNotFoundException;
import de.tom.ref.webshop.errorhandling.NotFoundException;
import de.tom.ref.webshop.registration.token.ConfirmationToken;
import de.tom.ref.webshop.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public Customer getCustomer(String username) {
        return customerRepository.findByEmail(username)
                .orElseThrow( () ->
                        new NotFoundException(
                                String.format(Constants.USER_NOT_FOUND, username))
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByEmail(username);
        if (customer.isPresent()) {
            log.info("user found in the database: {} role={}", username, getAuthorities(customer.get()));

            return new org.springframework.security.core.userdetails.User(
                    customer.get().getEmail(),
                    customer.get().getPassword(),
                    customer.get().getEnabled(),
                    !customer.get().getAccountExpired(),
                    !customer.get().getCredentialsExpired(),
                    !customer.get().getLocked(),
                    getAuthorities(customer.get()));
        } else {
            throw new UsernameNotFoundException(
                    String.format(Constants.USER_NOT_FOUND, username));
        }
    }

    public String signUpUser(Customer customer) {
        boolean userExist = customerRepository
                .findByEmail(customer.getEmail())
                .isPresent();
        if (userExist) {
            throw new IllegalStateException("user already exist");
        }

        // save new user in database
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        customer = save(customer);

        // create token and send it back to the user
        ConfirmationToken confirmationToken = new ConfirmationToken(
                ConfirmationTokenService.createToken(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                customer
        );
        confirmationTokenService.save(confirmationToken);
        return confirmationToken.getToken();
    }


}
