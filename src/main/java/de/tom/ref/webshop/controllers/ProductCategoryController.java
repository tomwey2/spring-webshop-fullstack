package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.ProductCategory;
import de.tom.ref.webshop.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ProductCategoryController {
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/product_categories",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ProductCategory> getProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory createProductCategory(String name) {
        return new ProductCategory(name);
    }
}
