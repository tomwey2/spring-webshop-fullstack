package de.tom.ref.webshop.carts;

import de.tom.ref.webshop.carts.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByCustomerId(@Param("customer_id") Integer customerId);
}
