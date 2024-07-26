package com.zenden.sports_store.Filters.Category;

import org.springframework.data.jpa.domain.Specification;

import com.zenden.sports_store.Classes.Category;

public class CategorySpecification {

    public static Specification<Category> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Category> descriptionLike(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }
}
