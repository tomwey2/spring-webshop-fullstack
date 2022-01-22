package de.tom.ref.webshop.services.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void save(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public static String createToken() {
        return UUID.randomUUID().toString();
    }

    public ConfirmationToken getToken(String token) {
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow( () ->
                        new IllegalStateException("token not found"));
    }

    public void setConfirmedAt(ConfirmationToken token) {
        token.setConfirmedAt(LocalDateTime.now());
        save(token);
    }
}
