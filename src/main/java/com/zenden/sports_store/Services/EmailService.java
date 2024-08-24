package com.zenden.sports_store.Services;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.zenden.sports_store.Configs.EmailServiceConfig;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailServiceConfig emailServiceConfig;

    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromString;

    public void sendMail(String username, String to, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Добро пожаловать в наш интернет-магазин спортивного оборудования!");
        helper.setFrom(fromString);
        String htmlContent = generateHtmlContent(username, token);
        helper.setText(htmlContent, true);
        addImage(helper);
        javaMailSender.send(helper.getMimeMessage());
    }

    private String generateHtmlContent(String username, String token) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("token", token);
        return templateEngine.process(emailServiceConfig.getEMAIL_TEMPLATE(), context);
    }

    private void addImage(MimeMessageHelper helper) throws MessagingException {
        FileSystemResource image = new FileSystemResource(new File(emailServiceConfig.getIMAGE_PATH()));
        if (image.exists()) {
            helper.addInline(emailServiceConfig.getIMAGE_ID(), image);
        } else {
            log.error("Изображение не найдено по пути: " + emailServiceConfig.getIMAGE_PATH());
        }
    }

}
