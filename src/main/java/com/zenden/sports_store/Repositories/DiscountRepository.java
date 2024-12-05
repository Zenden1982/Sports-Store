package com.zenden.sports_store.Repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zenden.sports_store.Classes.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {
    Optional<Discount> findByProductId(Long productId);

    List<Discount> findByExpiryDateBefore(LocalDateTime expiryDate);
}
