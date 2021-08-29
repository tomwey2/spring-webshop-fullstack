package de.tom.ref.webshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tom.ref.webshop.entities.Product;
import de.tom.ref.webshop.errorhandling.ProductNotFoundException;
import de.tom.ref.webshop.repositories.ProductRepository;
import de.tom.ref.webshop.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Slf4j
class ProductControllerTest {
    private String separator = "##### Execute test: {} #####";
    private String requestPath = "/api/products";

    private static List<Product> testProducts;
    private static Product testProduct1;
    private static Product testProduct2;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductRepository repo;
    @MockBean
    private ProductService service;

    @BeforeAll
    public static void init() {
        testProduct1 = new Product("Test1", null, BigDecimal.ZERO, 10);
        testProduct1.setId(1);
        testProduct2 = new Product("Test2", null, BigDecimal.ZERO, 5);
        testProduct2.setId(2);
        testProducts = new ArrayList<>();
        testProducts.add(testProduct1);
        testProducts.add(testProduct2);
    }

    @Test
    public void getAll() throws Exception {
        log.info(separator, "getAll");
        Mockito.when(repo.findAll()).thenReturn(testProducts);
        String url = requestPath + "";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        log.debug(actualResponse);
        // TODO: fixed that bug!
        //String expectedResponse = objectMapper.writeValueAsString(testProducts);
        //assertEquals(actualResponse, expectedResponse);
    }

    @Test
    void getByIdOk() throws Exception {
        // Positive test case for id that exists.
        log.info(separator, "getById(1)");
        String actualResponse;
        String url = requestPath + "/{id}";

        Mockito.when(repo.findById(1)).thenReturn(java.util.Optional.of(testProduct1));
        MvcResult mvcResultOk = mockMvc
                .perform(get(url, "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        actualResponse = mvcResultOk.getResponse().getContentAsString();
        log.debug(actualResponse);
        String expectedResponse = objectMapper.writeValueAsString(testProduct1);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    void getByIdFailed() throws Exception {
        // Negative test case for id that not exists. In this case the controller throw a not found exception.
        log.info(separator, "getById(4711) -- FAILED");
        String url = requestPath + "/{id}";

        Mockito.when(repo.findById(4711)).thenThrow(ProductNotFoundException.class);
        MvcResult mvcResultFailed = mockMvc
                .perform(get(url, "4711")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException))
                .andReturn();
        String actualResponse = mvcResultFailed.getResponse().getContentAsString();
        log.debug(actualResponse);
    }

}