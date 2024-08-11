package com.zenden.sports_store.Classes;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true, nullable = false, length=15)
    private String username;

    @Column(nullable = false, length=100)
    private String password;

    @Email
    @Column(unique = true, nullable = false, length=20)
    private String email;

    @Column(nullable = false, length=15)
    private String firstName;

    @Column(nullable = false, length=15)
    private String lastName;

    @Column(nullable = false, length=50)
    private String address;

    @Column(nullable = false, length=15)
    private String phoneNumber;

    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}
