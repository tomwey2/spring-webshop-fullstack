package de.tom.ref.webshop.services;

import de.tom.ref.webshop.entities.Product;
import de.tom.ref.webshop.entities.ProductCategory;
import de.tom.ref.webshop.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class ProductServiceTest {
    private String separator = "##### Execute test: {} #####";

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    private ProductCategory productCategory1;
    private ProductCategory productCategory2;
    private List<Product> products;

    @BeforeEach
    public void beforeEach() {
        productCategory1 = new ProductCategory("Category 1");
        productCategory1.setId(1);
        productCategory2 = new ProductCategory("Category 2");
        productCategory2.setId(2);

        products = new ArrayList<>();
        products.add(new Product("Product 1", productCategory1, BigDecimal.ZERO, 0));
        products.add(new Product("Product 2", productCategory1, BigDecimal.ZERO, 0));
        products.add(new Product("Product 3", productCategory2, BigDecimal.ZERO, 0));
    }

    @Test
    void getProducts() {
        log.debug(separator, "Test getProducts()");
        Mockito.when(productRepository.findAll()).thenReturn(products);

        assertEquals(3, productService.getProducts(null).size()); // get all products
        assertEquals(3, productService.getProducts(0).size()); // get all products
        assertEquals(2, productService.getProducts(1).size());
        assertEquals(1, productService.getProducts(2).size());
        assertEquals(0, productService.getProducts(3).size()); // list is empty if the category not exists
    }

    @Test
    void addProduct() {
        Product newProduct = new Product("new Product", productCategory1, BigDecimal.ZERO, 0);
        newProduct.setId(15);
        Mockito.when(productRepository.findProductByName("new Product")).thenReturn(Optional.empty());
        Mockito.when(productRepository.save(newProduct)).thenReturn(newProduct);

        Product result1 = productService.addProduct(newProduct);
        assertEquals(15, result1.getId());

        // if the name of the new product already exists then an exception is thrown
        newProduct.setName("Product 2");
        Mockito.when(productRepository.findProductByName("Product 2")).thenReturn(Optional.of(products.get(1)));
        assertThrows(IllegalStateException.class, () -> {productService.addProduct(newProduct);});

    }

    @Test
    void delProduct() {
        Mockito.when(productRepository.existsById(1)).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> {productService.delProduct(1);});
        assertThrows(IllegalStateException.class, () -> {productService.delProduct(null);});
    }
}