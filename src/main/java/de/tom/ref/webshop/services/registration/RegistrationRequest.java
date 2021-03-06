package de.tom.ref.webshop.services.registration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationRequest {
    private final String name;
    private final String email;
    private final String password;
}
