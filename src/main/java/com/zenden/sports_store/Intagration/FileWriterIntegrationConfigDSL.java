package com.zenden.sports_store.Intagration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FileWriterIntegrationConfigDSL {

    // @Bean
    // public IntegrationFlow fileWriterFlow() {
    // return IntegrationFlow
    // .from(MessageChannels.direct("textInputChannel"))
    // .<String, String>transform(text -> text.toUpperCase())
    // .<String>filter(text -> text.contains("SIA6"))
    // .route(p -> {
    // if (p.getClass().isAssignableFrom(String.class)) {
    // return "textFileChannel";
    // }
    // }, mapping -> mapping.subFlowMapping("textFileChannel",
    // txc -> txc.<String>handle((message, headers) -> {
    // File file = new File("tmp/sia6/files/" + message + ".txt");
    // try {
    // Files.outboundAdapter(file).write(message);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // })))
    // .handle(Files
    // .outboundAdapter(new File("tmp/sia6/files"))
    // .fileExistsMode(FileExistsMode.APPEND)
    // .appendNewLine(true))
    // .get();
    // }

    // private String route(Object p) {
    // return p.getClass().isAssignableFrom(String.class) ? "textFileChannel" :
    // null;
    // }

    // private String handle(Object message, Object headers) {

    // }

}
