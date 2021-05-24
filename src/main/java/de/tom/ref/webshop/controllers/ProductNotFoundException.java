package de.tom.ref.webshop.controllers;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Integer id) {
        super("Could not find product " + id);
    }
}
