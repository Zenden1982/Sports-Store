package com.zenden.sports_store.Configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zenden.sports_store.Filters.CurrencyFilter;
import com.zenden.sports_store.Services.UserSessionService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class FilterConfig {
    private final UserSessionService userSessionService;

    @Bean
    public FilterRegistrationBean<CurrencyFilter> registerCurrencyFilter() {
        FilterRegistrationBean<com.zenden.sports_store.Filters.CurrencyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new com.zenden.sports_store.Filters.CurrencyFilter(userSessionService));
        registrationBean.addUrlPatterns("/products/*");
        return registrationBean;
    }
}
