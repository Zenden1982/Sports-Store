package com.zenden.sports_store.Intagration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "textFileChannel")
public interface FileWriterGateway {
    void writeToFile(@Header(FileHeaders.FILENAME) String filename, String content);
}