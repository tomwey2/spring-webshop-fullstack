package de.tom.ref.webshop.entities.carts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query(value = "select * from carts c where c.customer_id = ?1", nativeQuery = true)
    Optional<Cart> findByCustomerId(Integer customerId);
}
