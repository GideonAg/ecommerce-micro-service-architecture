package com.redeemerlives.ecommerce.email;

import com.redeemerlives.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.redeemerlives.ecommerce.email.EmailTemplates.ORDER_CONFIRMATION;
import static com.redeemerlives.ecommerce.email.EmailTemplates.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(
            String targetEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setFrom("redeemerprogramming@gmail.com");
        helper.setSubject(PAYMENT_CONFIRMATION.getSubject());
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            helper.setText(htmlTemplate, true);
            helper.setTo(targetEmail);
            mailSender.send(message);
            log.info("INFO - Email successfully sent to {}", targetEmail);
        } catch (MessagingException exception) {
            log.warn("WARNING - Cannot send email to {}", targetEmail);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(
            String targetEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setFrom("redeemerprogramming@gmail.com");
        helper.setSubject(ORDER_CONFIRMATION.getSubject());
        helper.setTo(targetEmail);

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);
        final String templateName = ORDER_CONFIRMATION.getTemplate();

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            helper.setText(htmlTemplate, true);
            mailSender.send(message);
            log.info("INFO - Email successfully sent to {}", targetEmail);
        } catch (MessagingException exception) {
            log.warn("WARNING - Cannot send email to {}", targetEmail);
        }
    }
}
