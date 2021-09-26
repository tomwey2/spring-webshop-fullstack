package de.tom.ref.webshop.registration;

import de.tom.ref.webshop.entities.customers.Customer;
import de.tom.ref.webshop.entities.customers.CustomerService;
import de.tom.ref.webshop.enums.UserRole;
import de.tom.ref.webshop.registration.email.EmailSender;
import de.tom.ref.webshop.registration.email.EmailValidator;
import de.tom.ref.webshop.registration.token.ConfirmationToken;
import de.tom.ref.webshop.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final CustomerService customerService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest request) {
        if (!emailValidator.test(request.getEmail())) {
            throw new IllegalStateException("email not valid");
        }
        Customer customer = new Customer(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.ROLE_USER);
        String token = customerService.signUpUser(customer);
        String link = "http://localhost:8080/api/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                "confirm your email",
                buildEmail(request.getName(), link)
        );
        return token;
    }

    @Transactional
    public String confirmation(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(confirmationToken);
        customerService.enableCustomer(confirmationToken.getCustomer());
        return "confirmed";
    }

    private String buildEmail(String name,String  link) {
        String emailText = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>Hi " + name + ",</p>\n" +
                "<p>Thank you for registering at my test webshop.</p>\n" +
                "<p>Please click on the below link to activate your account:</p>\n" +
                "<p><a href=\"" + link + "\">Activate Now</a></p>\n" +
                "<p>The link will expire in 15 minutes.</p>\n" +
                "<p>Regards,<br>Thomas</p>\n" +
                "</body>\n" +
                "</html>\n";
        return emailText;
    }
}
