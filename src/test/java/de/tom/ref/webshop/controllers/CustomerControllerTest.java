package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.entities.Customer;
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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// The @SpringBootTest annotation tells Spring Boot to look for a main configuration class
// (one with @SpringBootApplication, for instance) and use that to start a Spring application context.
@SpringBootTest
class CustomerControllerTest {
    Logger log = LogManager.getLogger(CustomerControllerTest.class);
    String separator = "##### Execute test: {} #####";

    // Spring interprets the @Autowired annotation, and the controller is injected before the test methods are run.
    @Autowired
    private CustomerController controller;

    @Test
    void getAll() {
        log.info(separator, "getAll()");
        assertThat(controller).isNotNull();
        List<Customer> customers = controller.getAll();
        for (Customer customer : customers) {
            log.debug("Customer: {}", customer);
        }
    }

    @Test
    void getById() {
        log.info(separator, "getById()");
        assertThat(controller).isNotNull();
        Customer customer;
        // positive test case for id that exists
        Integer existId = 1;
        customer = controller.getById(existId);
        assertThat(customer).isNotNull();
        log.debug("Customer id={}: {} ", existId, customer);
        // negative test case for id that not exists
        try {
            Integer notExistId = 4711;
            customer = controller.getById(notExistId);
            assertThatExceptionOfType(CustomerNotFoundException.class);
        } catch (CustomerNotFoundException e) {
            log.debug(e);
        };
    }

    @Test
    void getByUsername() {
        log.info(separator, "getByUserName()");
        assertThat(controller).isNotNull();
        Customer customer;
        // positive test case for user that exists
        String existUser = "user1";
        customer = controller.getByUserName(existUser);
        assertThat(customer).isNotNull();
        log.debug("Customer username={}}: {} ", existUser, customer);
        // negative test case for user that not exists
        try {
            String notExistUser = "blabla";
            customer = controller.getByUserName(notExistUser);
            assertThatExceptionOfType(CustomerNotFoundException.class);
        } catch (CustomerNotFoundException e) {
            log.debug(e);
        };
    }

    @Test
    void create() {
        log.info(separator, "create()");
        assertThat(controller).isNotNull();
        Customer customer = controller.create("test", "John", "Doe", "",
                "", "", "", "", "");
        log.debug("Customer: {}", customer);
    }
}