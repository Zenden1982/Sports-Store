package com.zenden.sposts_store.Classes;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    @Email
    private String email;

    private String fistName;

    private String lastName;

    private String address;

    @NumberFormat
    private String phoneNumber;

    @CreatedDate
    private LocalDateTime registrationDate;

}
