package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.Product;
import de.tom.ref.webshop.entities.ProductCategory;
import de.tom.ref.webshop.repositories.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/products",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Product> getProducts(@RequestParam(value = "category_id", defaultValue = "0") Integer categoryId) {
        List<Product> products = productRepository.findAll();
        if (categoryId != 0) {
            products = products.stream()
                    .filter(product -> product.getCategory().getId() == categoryId)
                    .collect(Collectors.toList());
        }
        return products;
    }

    public Product createProduct(String name, ProductCategory category, BigDecimal unitPrice, Integer unitsInStock) {
        return new Product(name, category, unitPrice, unitsInStock);
    }
}
