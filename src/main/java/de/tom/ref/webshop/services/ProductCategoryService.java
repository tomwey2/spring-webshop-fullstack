package de.tom.ref.webshop.services;

import de.tom.ref.webshop.entities.ProductCategory;
import de.tom.ref.webshop.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    /**
     * Get the product category with the given id.
     *
     * @param categoryId the id of the product category in the database.
     * @return the product category object if exists otherwise throw an exception.
     */
    public ProductCategory getProductCategory(Integer categoryId) {
        Optional<ProductCategory> category = productCategoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new IllegalStateException("Product category with id=" + categoryId + " not found.");
        }
        return category.get();
    }

}
