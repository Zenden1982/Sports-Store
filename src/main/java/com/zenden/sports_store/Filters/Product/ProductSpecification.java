package com.zenden.sports_store.Filters.Product;

import org.springframework.data.jpa.domain.Specification;

import com.zenden.sports_store.Classes.Product;

public class ProductSpecification {

    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("productName"), "%" + name + "%");
    }

    public static Specification<Product> descriptionLike(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("productDescription"), "%" + description + "%");
    }

    public static Specification<Product> priceLessThan(long price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("price"), price);
    }

    public static Specification<Product> priceGreaterThan(long price) {
        return (root, query, criteriaBuiler) -> criteriaBuiler.greaterThan(root.get("price"), price);
    }

    public static Specification<Product> categoryEquals(long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("id"), id);
    }
}
