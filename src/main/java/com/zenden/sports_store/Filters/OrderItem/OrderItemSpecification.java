package com.zenden.sports_store.Filters.OrderItem;

import org.springframework.data.jpa.domain.Specification;

import com.zenden.sports_store.Classes.OrderItem;

public class OrderItemSpecification {

    public static Specification<OrderItem> orderEquals(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("order").get("id"), id);
    }
}
