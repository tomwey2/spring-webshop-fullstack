package de.tom.ref.webshop.services.email;

import de.tom.ref.webshop.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService implements EmailSender {
    private static String EMAIL_SEND_FAILED = "failed to send email";
    private final JavaMailSender mailSender;

    // install maildev smtp server from github.com > maildev > maildev
    // https://github.com/maildev/maildev

    @Override
    @Async
    public void send(String to, String subject, String text) {
        // send email only to the registered users, not to the fictive demo users
        if (Constants.LIST_OF_DEMO_USERS.contains(to)) {
            log.info("don't send email to user: {}", to);
            return;
        }

        log.info("send email to user: {}", to);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(text, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("info@webshop.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(EMAIL_SEND_FAILED, e);
            new IllegalStateException(EMAIL_SEND_FAILED);
        }
    }
}
