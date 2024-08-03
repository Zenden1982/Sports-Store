package com.zenden.sports_store.Filters;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.zenden.sports_store.Services.UserSessionService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CurrencyFilter extends HttpFilter {

    private final UserSessionService userSessionService;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String currency = request.getHeader("Currency");
        if (currency != null && !currency.isEmpty()) {
            userSessionService.setCurrency(currency);
        }
        chain.doFilter(request, response);
    }
}
