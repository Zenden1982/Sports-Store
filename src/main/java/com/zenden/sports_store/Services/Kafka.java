package com.zenden.sports_store.Services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class Kafka {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrder(String message) {
        kafkaTemplate.send("order", message);
    }
}
