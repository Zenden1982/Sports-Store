package com.zenden.sports_store.Controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@RestController
public class ChatGptController {

    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter chatGpt(@RequestBody String userMessage) {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        // Запускаем асинхронную обработку
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://localhost:1337/v1/chat/completions"; // URL вашего API
                    String model = "gpt-4o-mini"; // Модель API

                    // Создаем HTTP POST запрос
                    URL obj = new URL(url);
                    HttpURLConnection con;
                    con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    con.setRequestProperty("Accept-Charset", "UTF-8");

                    // Формируем тело запроса
                    JSONObject body = new JSONObject();
                    body.put("model", model);
                    body.put("stream", true);
                    JSONArray messages = new JSONArray();

                    // Задаем контекст для ассистента
                    String context = "Ты — помощник в спортивном магазине. Отвечай на вопросы о спорте, товарах и тренировках. На вопросы, не касающиеся спорта, не отвечай.";
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
                        if (inputLine.startsWith("data: ")) {
                            String jsonResponse = inputLine.substring(6); // Удаляем "data: "
                            if (!jsonResponse.trim().equals("[DONE]")) {
                                JSONObject responseObject = new JSONObject(jsonResponse);
                                String content = responseObject.getJSONArray("choices").getJSONObject(0)
                                        .getJSONObject("delta").getString("content");

                                // Отправляем данные потоком
                                emitter.send(content);
                            }
                        }
                    }
                    in.close();
                    emitter.complete(); // Завершаем поток

                } catch (Exception e) {
                    try {
                        emitter.completeWithError(e); // Если ошибка, сообщаем об этом
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();

        return emitter; // Возвращаем поток данных
    }
}