package com.zenden.sports_store.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zenden.sports_store.Classes.PaymentInfo;
import com.zenden.sports_store.Classes.Enum.OrderStatus;
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

    private final List<String> statuses = List.of("pending", "waiting_for_capture", "succeeded");

    public Payment createPayment(Double value, String currency, Long orderId) throws Exception {
        PaymentProcessor paymentProcessor = new PaymentProcessor(apiClient);
        String valueStr = String.valueOf(value);
        return paymentProcessor.create(Payment.builder()
                .amount(Amount.builder().value(valueStr).currency(currency).build())
                .description("Оплата заказа")
                .confirmation(Confirmation.builder()
                        .type(Confirmation.Type.REDIRECT)
                        .returnUrl("http://localhost:5173/")
                        .build())
                .build(), null);
    }

    public PaymentInfo getPayment(String paymentId) throws Exception {
        return checkPayment(paymentRepository.findById(paymentId).orElseThrow());
    }

    public PaymentInfo getPaymentInfoByOrder(Long orderId) {
        return checkPayment(paymentRepository.findByOrderId(orderId).orElseThrow());
    }

    @Scheduled(fixedRate = 5000)
    public void checkPayemntStatus() {
        List<PaymentInfo> paymentInfoList = paymentRepository.findByStatusIn(statuses);
        paymentInfoList.forEach(this::checkPayment);
    }

    public PaymentInfo checkPayment(PaymentInfo paymentInfo) {
        if (LocalDateTime.now().isAfter(paymentInfo.getCreatedDate().plusDays(1))) {
            paymentInfo.setStatus("canceled");
            paymentRepository.save(paymentInfo);
            return paymentInfo;
        }

        PaymentProcessor paymentProcessor = new PaymentProcessor(apiClient);
        Payment payment = paymentProcessor.findById(paymentInfo.getId());

        if ("waiting_for_capture".equals(payment.getStatus())) {
            payment = paymentProcessor.capture(payment.getId(), Payment.builder().build(), null);
        }

        if ("succeeded".equals(payment.getStatus())) {
            orderRepository.findById(paymentInfo.getOrder().getId())
                    .ifPresent(order -> {
                        if (OrderStatus.PENDING.equals(order.getStatus())) {
                            order.setStatus(OrderStatus.PROCESSING);
                            orderRepository.save(order);
                        }
                    });
        }
        updatePaymentInfoStatus(paymentInfo, payment);

        return paymentInfo;
    }

    private void updatePaymentInfoStatus(PaymentInfo paymentInfo, Payment payment) {
        if (!payment.getStatus().equals(paymentInfo.getStatus())) {
            paymentInfo.setStatus(payment.getStatus());
            paymentRepository.save(paymentInfo);
        }
    }
}
