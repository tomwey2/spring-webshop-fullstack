package de.tom.ref.webshop.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tom.ref.webshop.entities.Cart;
import de.tom.ref.webshop.entities.Customer;
import de.tom.ref.webshop.repositories.CartRepository;
import de.tom.ref.webshop.repositories.CustomerRepository;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    private final Customer customer = new Customer("John", "Doe","doe@test.com", "password",
            "Main Street","", "Munich", "80000", "Germany");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartRepository repo;

    @Test
    public void testgetAll() throws Exception {
        List<Cart> carts = new ArrayList<>();
        carts.add(new Cart(customer));
        Mockito.when(repo.findAll()).thenReturn(carts);
        String url = "/carts";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(actualResponse);
        String expectedResponse = objectMapper.writeValueAsString(carts);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void testSave() throws Exception {
        Cart newCart = new Cart(customer);
        Cart saveCart = new Cart(customer);
        saveCart.setId(1);

        Mockito.when(repo.save(newCart)).thenReturn(saveCart);
        String url = "/carts/save";
        mockMvc.perform(
                post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(newCart))
                .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().string("1"));

    }
}