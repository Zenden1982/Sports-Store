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
		return chatGptResponse(userMessage);
	}

	// Метод для разбора ответа

	private String chatGptResponse(String userMessage) {
		String url = "http://localhost:1337/v1/chat/completions"; // URL вашего API
		String model = "gpt-4o-mini"; // Модель API

		try {
			// Создаем HTTP POST запрос
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

			// Формируем тело запроса
			JSONObject body = new JSONObject();
			body.put("model", model);
			JSONArray messages = new JSONArray();

			// Контекст для ассистента
			JSONObject contextMessage = new JSONObject();
			contextMessage.put("role", "system");
			contextMessage.put("content",
					"Ты — помощник в спортивном магазине. Отвечай на вопросы о спорте, товарах и тренировках и не о чем больше.");
			messages.put(contextMessage);

			// Сообщение от пользователя
			JSONObject message = new JSONObject();
			message.put("role", "user");
			message.put("content", userMessage);
			messages.put(message);

			body.put("messages", messages);

			// Отправляем тело запроса
			con.setDoOutput(true);
			try (OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), "UTF-8")) {
				writer.write(body.toString());
			}

			// Получаем ответ
			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
				StringBuilder response = new StringBuilder();
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				return response.toString();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
