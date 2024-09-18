package com.zenden.sports_store.Intagration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

//@Configuration
public class FileWriterIntegrationConfig {

    @Bean
    @Transformer(inputChannel = "textFileChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {

        return text -> text.toUpperCase();
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("tmp/sia6/files"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);

        return handler;
    }

    @Bean
    @Router(inputChannel = "textFileChanngel")
    public AbstractMessageRouter twoOrMoreSymbols() {
        return new AbstractMessageRouter() {
            @Override
            protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
                String word = (String) message.getPayload();
                if (word.length() > 2) {
                    return Collections.singleton(evenChanngel());
                }
                return Collections.singleton(oddChangel());
            }
        };
    }

    @Bean
    public MessageChannel evenChanngel() {
        return new PublishSubscribeChannel();

    }

    @Bean
    public MessageChannel oddChangel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    @Splitter(inputChannel = "textFileChanngel", outputChannel = "splitChannel")
    public Collection<Object> splitMessage(Message<?> message) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(message.getPayload());
        list.add(message.getHeaders());
        return list;
    }

    @Bean
    @ServiceActivator(inputChannel = "splitChannel")
    public MessageHandler sysoutHandler() {
        return message -> {
            System.out.println(message);
        };
    }
}
