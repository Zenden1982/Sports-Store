package com.zenden.sports_store.Filters.Order;

import org.springframework.data.jpa.domain.Specification;

import com.zenden.sports_store.Classes.Order;
import com.zenden.sports_store.Classes.Enum.OrderStatus;


public class OrderSpecification {
    
    public static Specification<Order> statusEquals(OrderStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Order> userEquals(long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), id);
    }
}
