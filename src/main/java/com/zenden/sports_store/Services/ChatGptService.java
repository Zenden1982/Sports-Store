package com.zenden.sports_store.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@Service
public class ChatGptService {

    private final RestTemplate restTemplate;

    public ChatGptService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseBodyEmitter sendChatRequest(String userMessage) {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        // Асинхронная обработка
        new Thread(() -> {
            try {
                String url = "http://localhost:1337/v1/chat/completions";
                String model = "gpt-3.5-turbo";

                // Формируем тело запроса
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("model", model);
                requestBody.put("stream", true);

                List<Map<String, String>> messages = new ArrayList<>();
                messages.add(Map.of("role", "system", "content",
                        "Ты — помощник в спортивном магазине. Отвечай на вопросы о спорте, товарах и тренировках. На вопросы, не касающиеся спорта, не отвечай."));
                messages.add(Map.of("role", "user", "content", userMessage));

                requestBody.put("messages", messages);

                // Выполняем запрос
                ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);

                // Обрабатываем стрим ответа
                for (String line : response.getBody().split("\n")) {
                    if (line.startsWith("data: ")) {
                        String jsonResponse = line.substring(6); // Удаляем "data: "
                        if (!jsonResponse.trim().equals("[DONE]")) {
                            String content = new JSONObject(jsonResponse)
                                    .getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("delta")
                                    .getString("content");

                            // Отправляем данные через emitter
                            emitter.send(content);
                        }
                    }
                }
                emitter.complete();
            } catch (Exception e) {
                try {
                    emitter.completeWithError(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        return emitter;
    }
}
