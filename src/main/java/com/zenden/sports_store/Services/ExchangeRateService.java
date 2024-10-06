package com.zenden.sports_store.Services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExchangeRateService {

    private static final String PATH = "/ExchangeRate.json";

    private Double usd;
    private Double eur;
    private Double kzt;

    @Scheduled(fixedRate = 3600)
    public void updateExchangeRates() {
        try (InputStream inputStream = ExchangeRateService.class.getResourceAsStream(PATH)) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            usd = jsonNode.get("USD").asDouble();
            eur = jsonNode.get("EUR").asDouble();
            kzt = jsonNode.get("KZT").asDouble();
        } catch (IOException e) {
            log.error("Failed to read exchange rates from file: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Double getActualExchangeRate(Double price, String currency) {
        return switch (currency) {
            case "USD" -> price / usd;
            case "EUR" -> price / eur;
            case "KZT" -> price / kzt;
            default -> price;
        };
    }

    public Double getUSD() {
        return usd;
    }

    public Double getEUR() {
        return eur;
    }

    public Double getKZT() {
        return kzt;
    }
}
