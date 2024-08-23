package com.zenden.sports_store.Filters.User;

import lombok.Data;

@Data
public class UserFilter {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private Boolean enabled;
}
