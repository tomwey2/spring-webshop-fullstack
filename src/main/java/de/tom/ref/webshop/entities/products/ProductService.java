package de.tom.ref.webshop.entities.products;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryService productCategoryService;

    /**
     * Get all products from repository.
     * @param categoryId Filter the list of products with the category id.
     *                   If the id is null or 0 then get all products.
     * @return The list of products.
     */
    public List<Product> getProducts(Integer categoryId) {
        List<Product> products = productRepository.findAll();
        if (categoryId != null && categoryId != 0) {
            products = products.stream()
                    .filter(product -> product.getCategory().getId() == categoryId)
                    .collect(Collectors.toList());
        }
        return products;
    }

    /**
     * Get the product with the given id.
     *
     * @param productId the id of the product in the database.
     * @return the product object if exists otherwise throw an exception.
     */
    public Product getProduct(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new IllegalStateException("Product with id=" + productId + " not found.");
        }
        return product.get();
    }

    /**
     * Add a new product into the repository. If there is already a product with the same name
     * then an exception is thrown.
     *
     * @param categoryId the id of the category in the database.
     * @param product The product that should to add.
     * @return the products that was added if it not exists otherwise throw an exception.
     */
    public Product addProduct(Integer categoryId, Product product) {
        if (productRepository.findByName(product.getName()) != null) {
            throw new IllegalStateException("Product with name '" + product.getName() + "' exists.");
        }
        product.setCategory(productCategoryService.getProductCategory(categoryId));
        return productRepository.save(product);
    }

    public Product decreaseUnitsInStock(Product product, int quantity) {
        product.setUnitsInStock(product.getUnitsInStock() - quantity);
        return productRepository.save(product);
    }

    public Product increaseUnitsInStock(Product product, int quantity) {
        product.setUnitsInStock(product.getUnitsInStock() + quantity);
        return productRepository.save(product);
    }

    /**
     * Delete a product from repository.
     * @param productId The id of the product that should be deleted.
     */
    public void delProduct(Integer productId) {
        if (productId == null) {
            throw new IllegalStateException("Argument productId is null.");
        }
        if (!productRepository.existsById(productId)) {
            throw new IllegalStateException("Product with id=" + productId + " not exists.");
        }
        productRepository.deleteById(productId);
    }
}
