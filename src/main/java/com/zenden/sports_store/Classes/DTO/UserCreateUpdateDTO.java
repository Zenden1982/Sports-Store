package com.zenden.sports_store.Classes.DTO;

import lombok.Data;

@Data
public class UserCreateUpdateDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
}
