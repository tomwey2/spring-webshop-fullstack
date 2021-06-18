package de.tom.ref.webshop.repositories;

import de.tom.ref.webshop.entities.Cart;
import de.tom.ref.webshop.entities.CartContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartContentRepository extends JpaRepository<CartContent, Integer> {
    List<CartContent> findByCartId(@Param("cart_id") Integer cartId);
}

