package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.ProductCategory;
import de.tom.ref.webshop.errorhandling.ProductCategoryNotFoundException;
import de.tom.ref.webshop.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/product_categories")
public class ProductCategoryController {
    @Autowired
    ProductCategoryRepository repository;

    @GetMapping("")
    public List<ProductCategory> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ProductCategory getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductCategoryNotFoundException(id));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductCategory post(@RequestBody ProductCategory newCategory) {
        ProductCategory category = new ProductCategory(newCategory.getName());
        return repository.save(category);
    }

}
