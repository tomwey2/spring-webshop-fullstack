package de.tom.ref.webshop.repositories;

import de.tom.ref.webshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from products p where p.product_name = ?1", nativeQuery = true)
    Product findByName(String name);
}
