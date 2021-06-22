package de.tom.ref.webshop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Integer id) {
        super("Could not find customer with id=" + id);
    }
    public CustomerNotFoundException(String email) {
        super("Could not find customer with email=" + email);
    }
}
