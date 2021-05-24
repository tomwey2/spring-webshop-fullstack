package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.Product;
import de.tom.ref.webshop.entities.ProductCategory;
import de.tom.ref.webshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    @Autowired
    ProductRepository repository;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/products",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Product> getAll(@RequestParam(value = "category_id", defaultValue = "0") Integer categoryId) {
        List<Product> products = repository.findAll();
        if (categoryId != 0) {
            products = products.stream()
                    .filter(product -> product.getCategory().getId() == categoryId)
                    .collect(Collectors.toList());
        }
        return products;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/products/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Product getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product create(String name, ProductCategory category, BigDecimal unitPrice, Integer unitsInStock) {
        return new Product(name, category, unitPrice, unitsInStock);
    }
}
