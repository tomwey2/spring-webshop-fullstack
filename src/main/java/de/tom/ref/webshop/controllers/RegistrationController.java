package de.tom.ref.webshop.controllers;

import de.tom.ref.webshop.services.registration.RegistrationRequest;
import de.tom.ref.webshop.services.registration.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path = "/")
@AllArgsConstructor
@Slf4j
public class RegistrationController {

    private RegistrationService registrationService;

    @GetMapping("/signup")
    public ModelAndView hello(ModelAndView model) {
        RegistrationRequest request = new RegistrationRequest("", "", "");
        model.addObject("registrationRequest", request);
        model.setViewName("signup_form");
        return model;
    }

    @PostMapping("/register")
    public ModelAndView register(RegistrationRequest request, ModelAndView model) {
        log.info("Register of user={} email={}", request.getName(), request.getEmail());
        String token = registrationService.register(request);
        model.addObject("token", token);
        model.setViewName("signup_confirm");
        return model;
    }

    @GetMapping("/register_confirm")
    public ModelAndView confirm(@RequestParam(value = "token") String token, ModelAndView model) {
        log.info("Called register_confirmation with token={}", token);
        String result = registrationService.confirmation(token);
        log.info("Confirmation result: {}", result);
        model.setViewName("signup_success");
        return model;
    }

    // *** REST Interface for the ADMIN in order to test the functionality ***

    @PostMapping("/api/register")
    public String register(@RequestBody RegistrationRequest request) {
        String token = registrationService.register(request);
        log.info("Signup user={} email={} token={}", request.getName(), request.getEmail(), token);
        return token;
    }

    @GetMapping("/api/register_confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmation(token);
    }
}
