package de.tom.ref.webshop.registration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/registration")
@AllArgsConstructor
@Slf4j
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping("/signup")
    public String register(@RequestBody RegistrationRequest request) {
        String token = registrationService.register(request);
        log.info("Signup user={} email={} token={}", request.getName(), request.getEmail(), token);
        return token;
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmation(token);
    }
}
