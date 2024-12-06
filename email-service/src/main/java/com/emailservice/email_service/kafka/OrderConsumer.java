package com.emailservice.email_service.kafka;

import com.basedomins.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);
    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderEvent event) {
        try {
            LOGGER.info("Received OrderEvent: {}", event);
            processOrderEvent(event);
            // Sending email
            sendEmail(event);
        } catch (Exception e) {
            // Log any exceptions during message processing
            LOGGER.error("Error processing OrderEvent: {}", e.getMessage(), e);
        }
    }
    private void processOrderEvent(OrderEvent event) {
        // Example processing logic
        LOGGER.info("Processing OrderEvent with Order ID: {}", event.getOrder().getOrderId());
        LOGGER.info("Order Status: {}", event.getStatus());
        LOGGER.info("Order Message: {}", event.getMessage());
    }
    private void sendEmail(OrderEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("nareshaj95@mail.com"); // Replace with the recipient's email address
        message.setSubject("Order Update: " + event.getOrder().getOrderId());
        message.setText(String.format("Dear Customer,%n%nYour order status is: %s.%nMessage: %s.%n%nThank you for choosing us!",
                event.getStatus(), event.getMessage()));
        mailSender.send(message);
        LOGGER.info("Email sent successfully for Order ID: {}", event.getOrder().getOrderId());
    }
}


