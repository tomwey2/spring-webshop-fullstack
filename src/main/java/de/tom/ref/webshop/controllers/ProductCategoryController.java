package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.ProductCategory;
import de.tom.ref.webshop.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductCategoryController {
    @Autowired
    ProductCategoryRepository repository;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/product_categories",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ProductCategory> getAll() {
        return repository.findAll();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/product_categories/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductCategory getById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductCategoryNotFoundException(id));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/createCategory",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductCategory post(@RequestBody ProductCategory newCategory) {
        ProductCategory category = create(newCategory.getName());
        return repository.save(category);
    }

    public ProductCategory create(String name) {
        return new ProductCategory(name);
    }

}
