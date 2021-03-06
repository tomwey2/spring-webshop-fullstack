package de.tom.ref.webshop.entities.carts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartContentRepository extends JpaRepository<CartContent, Integer> {
    List<CartContent> findByCartId(@Param("cart_id") Integer cartId);
    CartContent findByProductId(@Param("product_id") Integer productId);

    @Query(value = "select * from cart_contents cc where cc.cart_id = ?1 and cc.product_id = ?2", nativeQuery = true)
    CartContent findByProductAndCartId(@Param("cart_id") Integer cartId,
                                       @Param("product_id") Integer productId);
}

