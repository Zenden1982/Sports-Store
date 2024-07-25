package com.zenden.sports_store.Classes.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserReadDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
