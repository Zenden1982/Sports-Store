package com.zenden.sports_store.Intagration;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

@Configuration
public class FileWriterIntegrationConfigDSL {

    @Bean
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlow
                .from(MessageChannels.direct("textInputChannel"))
                .<String, String>transform(text -> text.toUpperCase())
                .<String>filter(text -> text.contains("SIA6"))
                .route(p -> {

                })
                .handle(Files
                        .outboundAdapter(new File("tmp/sia6/files"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get();
    }
}
