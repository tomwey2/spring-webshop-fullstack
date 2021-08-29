package de.tom.ref.webshop.services;

import de.tom.ref.webshop.entities.Product;
import de.tom.ref.webshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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
     * Add a new product into the repository. If there is already a product with the same name
     * then an exception is thrown.
     * @param product The product that should to add.
     * @return the products that was added.
     */
    public Product addProduct(Product product) {
        Optional<Product> productOptional = productRepository.findProductByName(product.getName());
        if (productOptional.isPresent()) {
            throw new IllegalStateException("Product with name '" + product.getName() + "' exists.");
        }
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
