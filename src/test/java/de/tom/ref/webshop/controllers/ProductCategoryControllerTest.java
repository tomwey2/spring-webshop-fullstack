package de.tom.ref.webshop.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ProductCategoryController.class)
class ProductCategoryControllerTest {
    Logger log = LogManager.getLogger(ProductCategoryControllerTest.class);
/*
    private String separator = "##### Execute test: {} #####";
    private String requestPath = "/api/product_categories";

    @Autowired
    private MockMvc mockMvc;

    List<ProductCategory> testCategories = WebshopApplication.initProductCategories();

    @Test
    public void getAllEmployeesAPI() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .get(requestPath)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.product_categories").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.product_categories[*].category_id").isNotEmpty());
    }

    @Test
    void getAll() throws Exception {
        log.info(separator, "getAll()");
        String url = requestPath + "";
        Mockito.when(productCategoryRepository.findAll()).thenReturn(testCategories);

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        log.debug(actualResponse);
        String expectedResponse = objectMapper.writeValueAsString(testCategories);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    void getById() throws Exception {
        log.info(separator, "getById(1)");
        String url = requestPath + "/{id}";
        ProductCategory category = new ProductCategory("test category 1");
        category.setId(1);
        Mockito.when(repo.findById(1)).thenReturn(java.util.Optional.of(category));

        MvcResult mvcResult = mockMvc.perform(get(url, "1")).andExpect(status().isOk()).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        log.debug(actualResponse);
        String expectedResponse = objectMapper.writeValueAsString(category);
        assertEquals(actualResponse, expectedResponse);
    }
*/
}