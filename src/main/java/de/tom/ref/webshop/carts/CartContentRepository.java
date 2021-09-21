package de.tom.ref.webshop.carts;

import de.tom.ref.webshop.carts.CartContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartContentRepository extends JpaRepository<CartContent, Integer> {
    List<CartContent> findByCartId(@Param("cart_id") Integer cartId);
}

