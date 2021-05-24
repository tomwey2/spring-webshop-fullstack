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
class ProductCategoryControllerTest {
    Logger log = LogManager.getLogger(ProductCategoryControllerTest.class);

    // Spring interprets the @Autowired annotation, and the controller is injected before the test methods are run.
    @Autowired
    private ProductCategoryController controller;

    @Test
    void getProductCategories() {
        log.info("##### Execute test: getProductCategories #####");
        assertThat(controller).isNotNull();
        List<ProductCategory> categories = controller.getProductCategories();
        for (ProductCategory category : categories) {
            log.debug("Category: {}", category);
        }
    }

    @Test
    void createProduct() {
        log.info("##### Execute test: createProduct #####");
        assertThat(controller).isNotNull();
        ProductCategory category = controller.createProductCategory("Testcategory");
        log.debug("Category: {}", category);
    }
}