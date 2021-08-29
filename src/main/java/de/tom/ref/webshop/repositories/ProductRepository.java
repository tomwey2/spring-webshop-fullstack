package de.tom.ref.webshop.repositories;

import de.tom.ref.webshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Products p WHERE p.name = ?1")
    Optional<Product> findProductByName(String name);
}
