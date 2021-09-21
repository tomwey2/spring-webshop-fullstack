package de.tom.ref.webshop.services;

import de.tom.ref.webshop.entities.carts.*;
import de.tom.ref.webshop.entities.customers.Customer;
import de.tom.ref.webshop.enums.UserRole;
import de.tom.ref.webshop.entities.customers.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartContentRepository cartContentRepo;
    @Mock
    private CustomerRepository customerRepo;
    @Mock
    private CartRepository cartRepo;
    @InjectMocks
    private CartService cartService;

    @Test
    void addToCart() {
        CartContent testContent = new CartContent(1,1, 1);
        Mockito.when(cartContentRepo.save(testContent)).thenReturn(testContent);

        CartContent result = cartService.addToCart(1, 1);
        assertEquals(1, result.getCartId());
        assertEquals(1, result.getProductId());
        assertEquals(1, result.getQuantity());
    }

    @Test
    void getAmountOfProductsInCart() {
        List<CartContent> testContents = new ArrayList<>();
        testContents.add(new CartContent(1,1,1));
        testContents.add(new CartContent(1,2,1));
        testContents.add(new CartContent(1,3,1));
        Mockito.when(cartContentRepo.findByCartId(1)).thenReturn(testContents);
        int result = cartService.getAmountOfProductsInCart(1);
        assertEquals(3, result);
    }

    @Test
    void createCartForCustomer() {
        Customer testCustomer = new Customer(
                null, "Max", "Mustermann",
                "mustermann@gmail.com", "",
                "", "", "", "", "", true,
                null, null,
                true, false, UserRole.ROLE_USER);
        testCustomer.setId(1);
        Cart testCart = new Cart(testCustomer);
        Mockito.when(customerRepo.findById(1)).thenReturn(Optional.of(testCustomer));
        Mockito.when(cartRepo.save(testCart)).thenReturn(testCart);

        Cart result = cartService.createCartForCustomer(1);
        assertEquals(1, result.getCustomer().getId());

        Mockito.when(customerRepo.findById(2)).thenReturn(Optional.ofNullable(null));
        assertThrows(IllegalStateException.class, () -> {cartService.createCartForCustomer(2);});
    }
}