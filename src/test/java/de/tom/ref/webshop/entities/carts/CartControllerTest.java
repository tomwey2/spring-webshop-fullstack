package de.tom.ref.webshop.entities.carts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(CartController.class)
class CartControllerTest {
    Logger log = LogManager.getLogger(CartControllerTest.class);
/*
    private String separator = "##### Execute test: {} #####";
    private String requestPath = "/api/carts";

    private static List<Cart> testCarts;
    private static Cart testCart1;
    private static Customer testCustomer1;
    private static Customer testCustomer2;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartRepository repo;

    @BeforeAll
    public static void init() {
        testCustomer1 = new Customer("John", "Doe","doe@test.com", "password",
                "Main Street","", "Munich", "80000", "Germany");
        testCustomer1.setId(1);
        testCustomer2 = new Customer("Alfred", "Mustermann","mustermann@test.com", "",
                "","", "", "", "");
        testCustomer2.setId(2);
        testCart1 = new Cart(testCustomer1);
        testCart1.setId(1);
        testCarts = new ArrayList<>();
        testCarts.add(testCart1);
        testCarts.add(new Cart(testCustomer2));
    }

    @Test
    public void getAll() throws Exception {
        log.info(separator, "getAll()");
        Mockito.when(repo.findAll()).thenReturn(testCarts);
        String url = requestPath + "";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(actualResponse);
        String expectedResponse = objectMapper.writeValueAsString(testCarts);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    void getByIdOk() throws Exception {
        // Positive test case for id that exists.
        log.info(separator, "getById(1)");
        String actualResponse;
        String url = requestPath + "/{id}";

        Mockito.when(repo.findById(1)).thenReturn(java.util.Optional.of(testCart1));
        MvcResult mvcResultOk = mockMvc
                .perform(get(url, "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        actualResponse = mvcResultOk.getResponse().getContentAsString();
        log.debug(actualResponse);
        String expectedResponse = objectMapper.writeValueAsString(testCart1);
        assertEquals(actualResponse, expectedResponse);
    }


    @Test
    public void save() throws Exception {
        log.info(separator, "save()");
        Cart newCart = new Cart(testCustomer1);
        Cart saveCart = new Cart(testCustomer1);
        saveCart.setId(1);

        Mockito.when(repo.save(newCart)).thenReturn(saveCart);
        String url = requestPath + "/save";
        mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(newCart))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("1"));

    }
*/
}