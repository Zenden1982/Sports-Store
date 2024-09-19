package com.zenden.sports_store.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zenden.sports_store.Services.PaymentService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.loolzaaa.youkassa.model.Payment;
import ru.loolzaaa.youkassa.processors.PaymentProcessor;

@RestController
@Slf4j
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create-payment")
    public Payment createPayment(@RequestParam Double value, @RequestParam String currency, @RequestParam Long orderId)
            throws Exception {
        return paymentService.createPayment(value, currency, orderId);
    }

    @GetMapping("/payment-status/{paymentId}")
    public ResponseEntity<String> handleReturnUrl(@PathVariable String paymentId) throws Exception {
        // Получаем информацию о платеже с помощью SDK YooKassa
        Payment payment = paymentService.getPayment(paymentId);

        if ("waiting_for_capture".equals(payment.getStatus())) {
            PaymentProcessor paymentProcessor = new PaymentProcessor(paymentService.getApiClient());
            payment = paymentProcessor.capture(payment.getId(), Payment.builder().build(), null);

        }

        return ResponseEntity.ok(payment.getStatus());
    }
}
