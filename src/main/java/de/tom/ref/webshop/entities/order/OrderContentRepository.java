package de.tom.ref.webshop.entities.order;

import de.tom.ref.webshop.entities.carts.CartContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderContentRepository extends JpaRepository<OrderContent, Integer> {
    List<OrderContent> findByOrderId(@Param("order_id") Integer orderId);
    OrderContent findByProductId(@Param("product_id") Integer productId);

    @Query(value = "select * from order_content oc where oc.order_id = ?1 and cc.product_id = ?2",
            nativeQuery = true)
    CartContent findByProductAndOrderId(@Param("order_id") Integer orderId,
                                       @Param("product_id") Integer productId);
}
