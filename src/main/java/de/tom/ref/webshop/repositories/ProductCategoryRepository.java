package de.tom.ref.webshop.repositories;

import de.tom.ref.webshop.entities.Product;
import de.tom.ref.webshop.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    @Query(value = "select * from product_categories pc where pc.product_name = ?1", nativeQuery = true)
    Product findByName(String name);
}
