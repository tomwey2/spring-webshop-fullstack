package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.Product;
import de.tom.ref.webshop.entities.ProductCategory;
import de.tom.ref.webshop.errorhandling.ProductNotFoundException;
import de.tom.ref.webshop.repositories.ProductRepository;
import de.tom.ref.webshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    @Autowired
    ProductRepository repository;
    @Autowired
    ProductService service;

    @GetMapping("")
    public List<Product> getAll(@RequestParam(value = "category_id", defaultValue = "0") Integer categoryId) {
        return service.getProducts(categoryId);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody Product product) {
        service.addProduct(product);
    }

    @PostMapping("/del/{id}")
    public void delById(@PathVariable Integer id) {
        service.delProduct(id);
    }

    public Product create(String name, ProductCategory category, BigDecimal unitPrice, Integer unitsInStock) {
        return new Product(name, category, unitPrice, unitsInStock);
    }
}
