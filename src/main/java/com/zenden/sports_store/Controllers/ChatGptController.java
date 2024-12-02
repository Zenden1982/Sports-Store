package com.zenden.sports_store.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatGptController {

    @PostMapping("/chat")
    public String chatGpt(@RequestBody String userMessage) {
        String url = "http://localhost:1337/v1/chat/completions"; // URL вашего API
        String model = "gpt-4o-mini"; // Модель API
        StringBuilder responses = new StringBuilder();

        try {
            // Создаем HTTP POST запрос
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept-Charset", "UTF-8");

            // Формируем тело запроса
            JSONObject body = new JSONObject();
            body.put("model", model);
            body.put("stream", true); // Используем потоковый режим
            JSONArray messages = new JSONArray();

            // Задаем контекст для ассистента
            String context = "Ты — помощник в спортивном магазине. Отвечай на вопросы о спорте, товарах и тренировках и не о чем больше.";
            JSONObject contextMessage = new JSONObject();
            contextMessage.put("role", "system"); // Устанавливаем роль как 'system'
            contextMessage.put("content", context);
            messages.put(contextMessage);

            // Сообщение от пользователя
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", userMessage);
            messages.put(message);
            body.put("messages", messages);

            // Отправляем тело запроса
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            writer.write(body.toString());
            writer.flush();
            writer.close();

            // Получаем ответ
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;

            // Обрабатываем потоковые ответы
            while ((inputLine = in.readLine()) != null) {
                // Удаляем префикс "data: " и добавляем к ответу
                if (inputLine.startsWith("data: ")) {
                    String jsonResponse = inputLine.substring(6); // Удаляем "data: "
                    responses.append(parseResponse(jsonResponse)); // Обрабатываем каждую строку
                }
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}"; // Возвращаем ошибку в формате JSON
        }

        // Возвращаем собранный ответ
        return responses.toString();
    }

    // Метод для разбора ответа
    private String parseResponse(String response) {
        StringBuilder content = new StringBuilder();

        try {
            if (response.trim().equals("[DONE]")) {
                return ""; // Завершаем обработку
            }

            // Удаляем префикс "data:" из начала строки, если он есть
            response = response.trim().replace("data: ", "");

            // Проверка на валидность JSON
            if (response.startsWith("{")) {
                // Парсим JSON
                JSONObject jsonResponse = new JSONObject(response);

                // Проверяем, есть ли ключ "choices" и является ли он массивом
                if (jsonResponse.has("choices")) {
                    JSONArray choices = jsonResponse.getJSONArray("choices");

                    // Проверяем, есть ли в массиве элементы
                    if (choices.length() > 0) {
                        JSONObject choice = choices.getJSONObject(0);

                        // Проверяем, существует ли ключ "delta" и является ли он объектом
                        if (choice.has("delta")) {
                            JSONObject delta = choice.getJSONObject("delta");

                            // Проверяем, существует ли ключ "content"
                            if (delta.has("content")) {
                                String textContent = delta.getString("content");

                                // Добавляем извлеченное содержимое в строку результата
                                content.append(textContent);
                            }
                        }
                    }
                }
            } else {
                // Обработка случаев, когда ответ не является корректным JSON
                System.out.println("Received non-JSON response: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content.toString(); // Возвращаем результат
    }
}
