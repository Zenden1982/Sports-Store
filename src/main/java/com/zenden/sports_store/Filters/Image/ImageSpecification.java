package com.zenden.sports_store.Filters.Image;

import org.springframework.data.jpa.domain.Specification;

import com.zenden.sports_store.Classes.Image;

public class ImageSpecification {

    public static Specification<Image> filterByProductId(Long productId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("product").get("id"), productId);
    }
}
