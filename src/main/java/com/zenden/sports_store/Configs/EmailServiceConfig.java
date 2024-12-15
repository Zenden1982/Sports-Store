package com.zenden.sports_store.Configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "email.service")
@Data
@Component
public class EmailServiceConfig {

    private String EMAIL_TEMPLATE = "welcome-email";

    private String IMAGE_PATH = "src/main/resources/images/header.jpg";

    private String IMAGE_ID = "imageId";
}
