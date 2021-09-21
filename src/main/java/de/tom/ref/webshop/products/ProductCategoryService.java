package de.tom.ref.webshop.products;

import de.tom.ref.webshop.products.ProductCategory;
import de.tom.ref.webshop.products.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    /**
     * Get all product categories.
     *
     * @return list of product categories.
     */
    public List<ProductCategory> getAll() {
        return productCategoryRepository.findAll();
    }

    /**
     * Get the product category with the given id.
     *
     * @param categoryId the id of the product category in the database.
     * @return the product category object if exists otherwise throw an exception.
     */
    public ProductCategory getProductCategory(Integer categoryId) {
        return productCategoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new IllegalStateException("Product category with id=" + categoryId + " not found."));
    }

    /**
     * Add a new product category into the database.
     * The name of the new category must not be existed.
     *
     * @param productCategory the new category
     * @return
     */
    public ProductCategory addProductCategory(ProductCategory productCategory) {
        if (productCategoryRepository.findByName(productCategory.getName()) != null) {
            throw new IllegalStateException("Product category with name '" + productCategory.getName() + "' exists.");
        }
        return productCategoryRepository.save(productCategory);
    }

}
