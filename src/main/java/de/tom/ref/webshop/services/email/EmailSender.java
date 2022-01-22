package de.tom.ref.webshop.services.email;

public interface EmailSender {
    public void send(String to, String subject, String text);
}
