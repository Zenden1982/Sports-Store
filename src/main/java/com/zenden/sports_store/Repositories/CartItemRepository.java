package com.zenden.sports_store.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zenden.sports_store.Classes.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);

    void deleteAllByCartId(Long cartId);

    void deleteByCartId(Long cartId);
}
