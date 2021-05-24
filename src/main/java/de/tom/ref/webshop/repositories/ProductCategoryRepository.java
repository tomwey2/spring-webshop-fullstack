package de.tom.ref.webshop.repositories;

import de.tom.ref.webshop.entities.Product;
import de.tom.ref.webshop.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
