package com.zenden.sports_store.Services;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.zenden.sports_store.Classes.Test;

@Service
public class RabbitOrderMessagingService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder() {
        Test test = new Test();
        test.setTest("ok");
        test.setId(10L);
        MessageConverter converter = rabbitTemplate.getMessageConverter();
        MessageProperties properties = new MessageProperties();
        Message message = converter.toMessage(test, properties);

        rabbitTemplate.send("test.queue", message);
    }

    @Bean
    public Queue testQueue() {
        return new Queue("test.queue", true); // Создание очереди с именем 'test.queue'
    }

}
