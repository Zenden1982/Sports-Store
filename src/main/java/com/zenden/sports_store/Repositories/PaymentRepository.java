package com.zenden.sports_store.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zenden.sports_store.Classes.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {

}
