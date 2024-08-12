package com.zenden.sports_store.Services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@Component
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private TemplateEngine templateEngine;
    
    
    String imagePath = "src/main/resources/images/American Psycho.jpg";
    
    @Value("${spring.mail.username}")
    String fromString;
    
    public void sendMail(String username, String to) throws MessagingException {
        
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setTo(to);
        helper.setSubject("Добро пожаловать в наш интернет-магазин спортивного оборудования!");
        helper.setFrom(fromString);
        
        // Создание контекста для Thymeleaf
        Context context = new Context();
        context.setVariable("username", username);
        
        // Генерация HTML-содержимого письма
        String htmlContent = templateEngine.process("welcome-email", context);
        helper.setText(htmlContent, true); // true для HTML
        
        // Добавление изображения
        FileSystemResource image = new FileSystemResource(new File(imagePath));
        if (image.exists()) {
            helper.addInline("imageId", image); // "imageId" - идентификатор изображения в HTML
        } else {
            System.out.println("Изображение не найдено по пути: " + imagePath);
        }
        
        javaMailSender.send(helper.getMimeMessage());
    }
}
