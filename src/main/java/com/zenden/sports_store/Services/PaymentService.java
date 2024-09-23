package com.zenden.sports_store.Services;

import org.springframework.stereotype.Service;

import com.zenden.sports_store.Classes.PaymentInfo;
import com.zenden.sports_store.Repositories.OrderRepository;
import com.zenden.sports_store.Repositories.PaymentRepository;

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

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    // Создание платежа
    public Payment createPayment(Double value, String currency, Long orderId) throws Exception {
        PaymentProcessor paymentProcessor = new PaymentProcessor(apiClient);

        String valueStr = String.valueOf(value);
        Payment payment = paymentProcessor.create(Payment.builder()
                .amount(Amount.builder().value(valueStr).currency(currency).build())
                .description("Оплата заказа")
                .confirmation(Confirmation.builder()
                        .type(Confirmation.Type.REDIRECT)
                        .returnUrl("http://localhost:5173/")
                        .build())
                .build(), null);

        return payment;
    }

    // Получение информации о платеже
    public Payment getPayment(String paymentId) throws Exception {
        PaymentProcessor paymentProcessor = new PaymentProcessor(apiClient);
        Payment payment = paymentProcessor.findById(paymentId);
        PaymentInfo paymentInfo = paymentRepository.findById(paymentId).get();
        if (!payment.getStatus().equals(paymentInfo.getStatus())) {
            paymentInfo.setStatus(payment.getStatus());
            paymentRepository.save(paymentInfo);
        }
        return payment;
    }

    public PaymentInfo getPaymentInfoByOrder(Long orderId) {
        return paymentRepository.findByOrderId(orderId).get();
    }

}
