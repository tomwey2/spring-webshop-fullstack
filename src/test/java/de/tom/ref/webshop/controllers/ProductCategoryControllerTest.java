package de.tom.ref.webshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tom.ref.webshop.entities.Cart;
import de.tom.ref.webshop.entities.ProductCategory;
import de.tom.ref.webshop.repositories.CartRepository;
import de.tom.ref.webshop.repositories.ProductCategoryRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductCategoryController.class)
class ProductCategoryControllerTest {
    Logger log = LogManager.getLogger(ProductCategoryControllerTest.class);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductCategoryRepository repo;

    @Test
    void getAll() throws Exception {
        log.info("##### Execute test: getAll #####");
        List<ProductCategory> categories = new ArrayList<>();
        categories.add(new ProductCategory("test category 1"));
        categories.add(new ProductCategory("test category 2"));
        categories.add(new ProductCategory("test category 3"));
        Mockito.when(repo.findAll()).thenReturn(categories);
        String url = "/product_categories";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        log.debug(actualResponse);
        String expectedResponse = objectMapper.writeValueAsString(categories);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    void getById() throws Exception {
        log.info("##### Execute test: getById #####");
        ProductCategory category = new ProductCategory("test category 1");
        category.setId(1);
        Mockito.when(repo.findById(1)).thenReturn(java.util.Optional.of(category));
        String url = "/product_categories/1";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        log.debug(actualResponse);
        String expectedResponse = objectMapper.writeValueAsString(category);
        assertEquals(actualResponse, expectedResponse);
    }

}