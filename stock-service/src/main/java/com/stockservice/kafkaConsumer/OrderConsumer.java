package com.stockservice.kafkaConsumer;

import com.basedomins.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics ="${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderEvent event) {
        try {
            LOGGER.info("Received OrderEvent: {}", event);
        } catch (Exception e) {
            // Log any exceptions during message processing
            LOGGER.error("Error processing OrderEvent: {}", e.getMessage(), e);
        }
    }
    private void processOrderEvent(OrderEvent event) {
        // Example processing logic (extend this as per your business needs)
        LOGGER.info("Processing OrderEvent with Order ID: {}", event.getOrder().getOrderId());
        LOGGER.info("Order Status: {}", event.getStatus());
        LOGGER.info("Order Message: {}", event.getMessage());
    }
}

