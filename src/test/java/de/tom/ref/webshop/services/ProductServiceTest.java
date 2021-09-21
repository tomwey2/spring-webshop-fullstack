package de.tom.ref.webshop.services;

import de.tom.ref.webshop.WebshopApplication;
import de.tom.ref.webshop.entities.products.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class ProductServiceTest {
    private final String separator = "##### Execute test: {} #####";

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryService productCategoryService;
    private final ProductService productService;

    private List<ProductCategory> testCategories;
    private List<Product> testProducts;

    public ProductServiceTest() {
        // create the mocked classes
        productRepository = Mockito.mock(ProductRepository.class);
        productCategoryRepository = Mockito.mock(ProductCategoryRepository.class);
        // instantiate the service classes
        productCategoryService = new ProductCategoryService(productCategoryRepository);
        productService = new ProductService(productRepository, productCategoryService);
    }

    @BeforeEach
    public void beforeEach() {
        testCategories = WebshopApplication.initProductCategories();
        int index = 1;
        for (ProductCategory category: testCategories) {
            category.setId(index);
            index++;
        }
        testProducts = WebshopApplication.initProducts(testCategories);
        index = 1;
        for (Product product: testProducts) {
            product.setId(index);
            index++;
        }
    }

    @Test
    void getProducts() {
        log.debug(separator, "Test getProducts()");
        Mockito.when(productRepository.findAll()).thenReturn(testProducts);

        assertEquals(testProducts.size(), productService.getProducts(null).size()); // get all products
        assertEquals(testProducts.size(), productService.getProducts(0).size()); // get all products
        assertEquals(2, productService.getProducts(1).size());
        assertEquals(3, productService.getProducts(2).size());
        assertEquals(0, productService.getProducts(5).size()); // list is empty if the category not exists
    }

    @Test
    void addNewProduct() {
        log.debug(separator, "Test addNewProduct()");
        String newProductName = "new Product";
        Product newProduct = new Product(newProductName, null, BigDecimal.ZERO, 0);
        Integer categoryId = 1;
        ProductCategory category = new ProductCategory("Test Category");
        category.setId(categoryId);
        Mockito.when(productRepository.findByName(newProductName))
                .thenReturn(null);
        Mockito.when(productRepository.save(newProduct))
                .thenReturn(newProduct);
        Mockito.when(productCategoryRepository.findById(categoryId))
                .thenReturn(Optional.of(category));

        Product result1 = productService.addProduct(categoryId, newProduct);
        assertEquals(categoryId, result1.getCategory().getId());
    }

    @Test
    void addExistingProduct() {
        log.debug(separator, "Test addExistingProduct()");
        String existingProductName = "existing Product";
        Product existingProduct = new Product(existingProductName, null, BigDecimal.ZERO, 0);
        Integer categoryId = 1;
        ProductCategory category = new ProductCategory("Test Category");
        category.setId(categoryId);
        Mockito.when(productRepository.findByName(existingProductName))
                .thenReturn(existingProduct);
        Mockito.when(productCategoryRepository.findById(categoryId))
                .thenReturn(Optional.of(category));

        assertThrows(IllegalStateException.class, () -> {
            productService.addProduct(categoryId, existingProduct);
        });
    }

    @Test
    void delProduct() {
        log.debug(separator, "Test delProduct()");
        Mockito.when(productRepository.existsById(1)).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> {
            productService.delProduct(1);
        });
        assertThrows(IllegalStateException.class, () -> {
            productService.delProduct(null);
        });
    }
}