package com.zenden.sports_store.Filters.User;

import org.springframework.data.jpa.domain.Specification;

import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Classes.Enum.Role;

public class UserSpecification {

    public static Specification<User> usernameLike(String username) {
        return (root, criteriaQuery, criteriaBuilder) -> 
            criteriaBuilder.like(root.get("username"), "%" + username + "%");
    }

    public static Specification<User> emailLike(String email) {
        return (root, criteriaQuery, criteriaBuilder) -> 
            criteriaBuilder.like(root.get("email"), "%" + email + "%");
    }

    public static Specification<User> firstNameLike(String firstName) {
        return (root, criteriaQuery, criteriaBuilder) -> 
            criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
    }

    public static Specification<User> lastNameLike(String lastName) {
        return (root, criteriaQuery, criteriaBuilder) -> 
            criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%");
    }

    public static Specification<User> phoneNumberLike(String phoneNumber) {
        return (root, criteriaQuery, criteriaBuilder) -> 
            criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%");
    }

    public static Specification<User> addressLike(String address) {
        return (root, criteriaQuery, criteriaBuilder) -> 
            criteriaBuilder.like(root.get("address"), "%" + address + "%");
    }

    public static Specification<User> roleEquals(Role role) {
        return (root, criteriaQuery, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("role"), role);
    }
}
