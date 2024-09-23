package com.zenden.sports_store.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zenden.sports_store.Classes.PaymentInfo;
import com.zenden.sports_store.Repositories.OrderRepository;
import com.zenden.sports_store.Repositories.PaymentRepository;

import jakarta.transaction.Transactional;
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
@Transactional
public class PaymentService {

    private final ApiClient apiClient;

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    List<String> statuses = new ArrayList<>();

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
    public PaymentInfo getPayment(String paymentId) throws Exception {
        PaymentInfo paymentInfo = paymentRepository.findById(paymentId).get();
        checkPayment(paymentInfo);
        return paymentInfo;
    }

    public PaymentInfo getPaymentInfoByOrder(Long orderId) {
        return checkPayment(paymentRepository.findByOrderId(orderId).get());
    }

    @Scheduled(fixedRate = 5000)
    public void checkPayemntStatus() {
        statuses.add("pending");
        statuses.add("waiting_for_capture");

        List<PaymentInfo> paymentInfoList = paymentRepository.findByStatusIn(statuses);
        if (!paymentInfoList.isEmpty()) {
            for (PaymentInfo paymentInfo : paymentInfoList) {
                checkPayment(paymentInfo);
            }

        }
    }

    public void equalsPayments(PaymentInfo paymentInfo, Payment payment) {
        if (!payment.getStatus().equals(paymentInfo.getStatus())) {
            paymentInfo.setStatus(payment.getStatus());
            paymentRepository.save(paymentInfo);
        }
    }

    public PaymentInfo checkPayment(PaymentInfo paymentInfo) {
        if (LocalDateTime.now().isAfter(paymentInfo.getCreatedDate().plusDays(1))) {
            paymentInfo.setStatus("canceled");
            paymentRepository.save(paymentInfo);
        }
        PaymentProcessor paymentProcessor = new PaymentProcessor(apiClient);
        Payment payment = paymentProcessor.findById(paymentInfo.getId());
        if (payment.getStatus().equals("waiting_for_capture")) {
            payment = paymentProcessor.capture(payment.getId(), Payment.builder().build(), null);

        }
        equalsPayments(paymentInfo, payment);
        return paymentInfo;
    }
}
