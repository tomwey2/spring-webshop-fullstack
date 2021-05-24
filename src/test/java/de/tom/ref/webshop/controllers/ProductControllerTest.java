package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.Product;
import de.tom.ref.webshop.entities.ProductCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// The @SpringBootTest annotation tells Spring Boot to look for a main configuration class
// (one with @SpringBootApplication, for instance) and use that to start a Spring application context.
@SpringBootTest
class ProductControllerTest {
    Logger log = LogManager.getLogger(ProductControllerTest.class);

    // Spring interprets the @Autowired annotation, and the controller is injected before the test methods are run.
    @Autowired
    private ProductController controller;
    @Autowired
    private ProductCategoryController productCategoryController;

    @Test
    void getAll() {
        log.info("##### Execute test: getProducts #####");
        assertThat(controller).isNotNull();
        List<Product> products = controller.getAll(0);
        for (Product product : products) {
            log.debug("Product: {} category: {}", product, product.getCategory());
        }
    }

    @Test
    void create() {
        log.info("##### Execute test: createProduct #####");
        assertThat(controller).isNotNull();
        ProductCategory category = productCategoryController.create("Testcategory");
        Product product = controller.create("Testproduct", category, new BigDecimal(10.50), 5);
        log.debug("Product: {}", product);
    }
}