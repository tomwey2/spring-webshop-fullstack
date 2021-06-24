package de.tom.ref.webshop.errorhandling;

public class ProductCategoryNotFoundException extends RuntimeException{
    public ProductCategoryNotFoundException(Integer id) {
        super("Could not find product category " + id);
    }
}
