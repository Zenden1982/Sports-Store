package com.zenden.sports_store.Classes.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class UserReadDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private Boolean enabled;
    private List<String> roles;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
