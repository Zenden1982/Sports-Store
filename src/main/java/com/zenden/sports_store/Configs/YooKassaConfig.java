package com.zenden.sports_store.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.ApiClientBuilder;

@Configuration
public class YooKassaConfig {

    @Bean
    public ApiClient apiClient() {
        return ApiClientBuilder.newBuilder()
                .configureBasicAuth("459219", "test_2yZdApg7QUotEQ0bnKn2e2X875w0NeVIoJZXih7TeB0")
                .build();
    }
}
