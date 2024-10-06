package com.zenden.sports_store.Filters;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.zenden.sports_store.Services.UserSessionService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

//Не нужный фильтр
@Component
@AllArgsConstructor
public class CurrencyFilter {

    private final UserSessionService userSessionService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // String currency = request.getHeader("Currency");
        // if (currency != null && !currency.isEmpty()) {
        // userSessionService.setCurrency(currency);
        // }
        // chain.doFilter(request, response);
    }

}
