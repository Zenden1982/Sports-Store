package com.zenden.sports_store.Services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExchangeRateService {

    @Autowired
    private UserSessionService userSessionService;

    private static final String PATH = "/ExchangeRate.json";

    private Double USD, EUR, KZT;

    @Scheduled(fixedRate = 3600)
    public void GetExchange(){
        try(InputStream inputStream = ExchangeRateService.class.getResourceAsStream(PATH)) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(inputStream);
            USD = rootNode.get("USD").asDouble();
            EUR = rootNode.get("EUR").asDouble();
            KZT = rootNode.get("KZT").asDouble();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Double getActualExchangeRate(Double price) {
        String currency = userSessionService.getCurrency();
        switch (currency) {
            case "USD":
                return price / USD;
            case "EUR":
                return price / EUR;
            case "KZT":
                return price / KZT;
            default:
                return (double)price;
        }
    }

    public Double getUSD() {
        return USD;
    }

    public Double getEUR() {
        return EUR;
    }

    public Double getKZT() {
        return KZT;
    }

}
