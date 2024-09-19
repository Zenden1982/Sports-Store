package com.zenden.sports_store.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zenden.sports_store.Classes.PaymentInfo;

public interface PaymentRepository extends JpaRepository<PaymentInfo, String> {
    Optional<PaymentInfo> findByOrderId(Long orderId);
}
