package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.products.ProductCategory;
import de.tom.ref.webshop.products.ProductCategoryRepository;
import de.tom.ref.webshop.products.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/product_categories")
public class ProductCategoryController {
    ProductCategoryRepository repository;
    ProductCategoryService service;

    @Autowired
    public ProductCategoryController(ProductCategoryRepository repository,
                                     ProductCategoryService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("")
    public List<ProductCategory> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ProductCategory getById(@PathVariable Integer id) {
        return service.getProductCategory(id);
    }

    @PostMapping("/add")
    public ProductCategory post(@RequestBody ProductCategory newCategory) {
        return service.addProductCategory(newCategory);
    }

}
