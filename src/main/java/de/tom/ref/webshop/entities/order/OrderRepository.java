package de.tom.ref.webshop.entities.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "select * from orders o where o.customer_id = ?1", nativeQuery = true)
    List<Order> findByCustomerId(Integer customerId);

    @Query(value = "select * from orders o where o.order_number = ?1", nativeQuery = true)
    Optional<Order> findByOrderNumber(String number);
}
