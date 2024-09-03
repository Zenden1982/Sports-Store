package com.zenden.sports_store.Artemis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.zenden.sports_store.Classes.Order;

@Service
public class JmsOrderMessagingService {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendOrder(Order order) {
        jmsTemplate.convertAndSend("order", order);
    }

    public void sendAndConvertOrder() {
        jmsTemplate.convertAndSend("order", "Hello", message -> {
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }
}
