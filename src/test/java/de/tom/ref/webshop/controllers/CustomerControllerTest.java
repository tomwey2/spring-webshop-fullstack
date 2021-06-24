package de.tom.ref.webshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tom.ref.webshop.entities.Customer;
import de.tom.ref.webshop.errorhandling.CustomerNotFoundException;
import de.tom.ref.webshop.repositories.CustomerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    private Logger log = LogManager.getLogger(CustomerControllerTest.class);
    private String separator = "##### Execute test: {} #####";
    private String requestPath = "/api/customers";

    private static List<Customer> testCustomers;
    private static Customer testCustomer1;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CustomerRepository repo;

    @BeforeAll
    public static void init() {
        testCustomer1 = new Customer("Alfred", "Mustermann", "mustermann@test.com");
        testCustomer1.setId(1);
        testCustomers = new ArrayList<>();
        testCustomers.add(testCustomer1);
        testCustomers.add(new Customer("Ingrid", "Mustermann", "mustermann@test.com"));
        testCustomers.add(new Customer("John", "Doe", "doe@test.com"));
    }

    @Test
    public void getAll() throws Exception {
        log.info(separator, "getAll");
        Mockito.when(repo.findAll()).thenReturn(testCustomers);
        String url = requestPath + "";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        log.debug(actualResponse);
        String expectedResponse = objectMapper.writeValueAsString(testCustomers);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    void getByIdOk() throws Exception {
        // Positive test case for id that exists.
        log.info(separator, "getById(1)");
        String actualResponse;
        String url = requestPath + "/{id}";

        Mockito.when(repo.findById(1)).thenReturn(java.util.Optional.of(testCustomer1));
        MvcResult mvcResultOk = mockMvc
                .perform(get(url, "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        actualResponse = mvcResultOk.getResponse().getContentAsString();
        log.debug(actualResponse);
        String expectedResponse = objectMapper.writeValueAsString(testCustomer1);
        assertEquals(actualResponse, expectedResponse);
   }

    @Test
    void getByIdFailed() throws Exception {
        // Negative test case for id that not exists. In this case the controller throw a not found exception.
        log.info(separator, "getById(4711) -- FAILED");
        String url = requestPath + "/{id}";

        Mockito.when(repo.findById(4711)).thenThrow(CustomerNotFoundException.class);
        MvcResult mvcResultFailed = mockMvc
                .perform(get(url, "4711")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerNotFoundException))
                .andReturn();
        String actualResponse = mvcResultFailed.getResponse().getContentAsString();
        log.debug(actualResponse);
    }

    @Test
    void getByEmail() throws Exception {
        log.info(separator, "getById(1)");
        String actualResponse;
        String url = requestPath + "/email/{email}";

        Mockito.when(repo.findByEmail("mustermann@test.com")).thenReturn(java.util.Optional.of(testCustomer1));
        MvcResult mvcResultOk = mockMvc
                .perform(get(url, "mustermann@test.com").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        actualResponse = mvcResultOk.getResponse().getContentAsString();
        log.debug(actualResponse);
        String expectedResponse = objectMapper.writeValueAsString(testCustomer1);
        assertEquals(actualResponse, expectedResponse);
    }

}