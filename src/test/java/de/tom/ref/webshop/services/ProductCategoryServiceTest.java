package de.tom.ref.webshop.services;

import de.tom.ref.webshop.WebshopApplication;
import de.tom.ref.webshop.entities.products.ProductCategory;
import de.tom.ref.webshop.entities.products.ProductCategoryRepository;
import de.tom.ref.webshop.entities.products.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class ProductCategoryServiceTest {
    private final String separator = "##### Execute test: {} #####";

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryService productCategoryService;

    private List<ProductCategory> testCategories;

    public ProductCategoryServiceTest() {
        // create the mocked classes
        productCategoryRepository = Mockito.mock(ProductCategoryRepository.class);
        // instantiate the service classes
        productCategoryService = new ProductCategoryService(productCategoryRepository);
    }

    @BeforeEach
    public void beforeEach() {
        testCategories = WebshopApplication.initProductCategories();
        int index = 1;
        for (ProductCategory category: testCategories) {
            category.setId(index);
            index++;
        }
    }

    @Test
    public void getAll() {
        log.debug(separator, "Test getAll()");
        Mockito.when(productCategoryRepository.findAll())
                .thenReturn(testCategories);
        assertEquals(testCategories.size(), productCategoryService.getAll().size());
    }

    @Test
    public void getExistingProductCategory() {
        log.debug(separator, "Test getExistingProductCategory()");
        ProductCategory existingCategory = testCategories.get(0);
        Mockito.when(productCategoryRepository.findById(existingCategory.getId()))
                .thenReturn(Optional.of(existingCategory));
        assertEquals(existingCategory, productCategoryService.getProductCategory(existingCategory.getId()));
    }

    @Test
    public void getNotExistingProductCategory() {
        log.debug(separator, "Test getNotExistingProductCategory()");
        Integer notExistingCategoryId = 4711;
        Mockito.when(productCategoryRepository.findById(notExistingCategoryId))
                .thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            productCategoryService.getProductCategory(notExistingCategoryId);
        });
    }
}
