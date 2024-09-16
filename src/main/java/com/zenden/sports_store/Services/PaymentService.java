package com.zenden.sports_store.Services;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.model.Payment;
import ru.loolzaaa.youkassa.pojo.Amount;
import ru.loolzaaa.youkassa.pojo.Confirmation;
import ru.loolzaaa.youkassa.processors.PaymentProcessor;

@AllArgsConstructor
@Service
@Data
public class PaymentService {

    private final ApiClient apiClient;

    // Создание платежа
    public Payment createPayment(String value, String currency) throws Exception {
        PaymentProcessor paymentProcessor = new PaymentProcessor(apiClient);

        Payment payment = paymentProcessor.create(Payment.builder()
                .amount(Amount.builder().value(value).currency(currency).build())
                .description("Оплата заказа")
                .confirmation(Confirmation.builder()
                        .type(Confirmation.Type.REDIRECT)
                        .returnUrl("http://localhost:8080/swagger-ui/index.html#")
                        .build())
                .build(), null);

        return payment;
    }

    // Получение информации о платеже
    public Payment getPayment(String paymentId) throws Exception {
        PaymentProcessor paymentProcessor = new PaymentProcessor(apiClient);
        return paymentProcessor.findById(paymentId);
    }
}
