package com.zenden.sports_store.Classes;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, length=15)
    private String username;

    @Column(nullable = false, length=100)
    private String password;

    @Email
    @Column(unique = true, nullable = false, length=20)
    private String email;

    @Column(nullable = false, length=15)
    private String fistName;

    @Column(nullable = false, length=15)
    private String lastName;

    @Column(nullable = false, length=50)
    private String address;

    @Column(nullable = false, length=10)
    private String phoneNumber;

    @CreatedDate
    private LocalDateTime registrationDate;

}
