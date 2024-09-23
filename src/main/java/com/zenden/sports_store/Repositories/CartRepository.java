package com.zenden.sports_store.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zenden.sports_store.Classes.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
