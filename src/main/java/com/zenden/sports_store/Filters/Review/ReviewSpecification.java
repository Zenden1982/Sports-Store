package com.zenden.sports_store.Filters.Review;

import org.springframework.data.jpa.domain.Specification;

import com.zenden.sports_store.Classes.Review;

public class ReviewSpecification {

    public static Specification<Review> productIdEquals(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("product").get("id"), id);
    }

    public static Specification<Review> userIdEquals(Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), id);
    }

    public static Specification<Review> ratingLess(int rating) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("rating"), rating);
    }

    public static Specification<Review> ratingGreaterThan(int rating) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating);
    }
}
