package com.zenden.sports_store.Classes.DTO;

import java.time.LocalDateTime;

import com.zenden.sports_store.Classes.Enum.Role;

import lombok.Data;

@Data
public class UserReadDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private Role role;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
