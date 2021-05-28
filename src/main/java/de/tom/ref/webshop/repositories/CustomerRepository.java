package de.tom.ref.webshop.repositories;

import de.tom.ref.webshop.entities.Customer;
import de.tom.ref.webshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(@Param("email") String email);
}
