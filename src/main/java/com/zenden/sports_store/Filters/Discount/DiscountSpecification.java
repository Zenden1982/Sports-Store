package com.zenden.sports_store.Filters.Discount;

import org.springframework.data.jpa.domain.Specification;

import com.zenden.sports_store.Classes.Discount;

public class DiscountSpecification {

    public static Specification<Discount> productEquals(Long id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("product").get("id"), id);
    }

    public static Specification<Discount> percentageLess(int percentage){
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("percentage"), percentage);
    }

    public static Specification<Discount> percentageGreaterThan(int percentage){
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("percentage"), percentage);
    }
}
