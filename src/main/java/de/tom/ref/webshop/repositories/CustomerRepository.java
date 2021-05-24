package de.tom.ref.webshop.repositories;

import de.tom.ref.webshop.entities.Customer;
import de.tom.ref.webshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
