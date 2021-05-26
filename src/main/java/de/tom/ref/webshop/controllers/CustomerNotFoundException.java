package de.tom.ref.webshop.controllers;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Integer id) {
        super("Could not find customer " + id);
    }
    public CustomerNotFoundException(String userName) {
        super("Could not find customer " + userName);
    }
}
