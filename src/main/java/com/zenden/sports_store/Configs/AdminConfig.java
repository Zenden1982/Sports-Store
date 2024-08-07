package com.zenden.sports_store.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Classes.Enum.Role;

@Configuration
public class AdminConfig {
    
    @Bean("admin")
    public User admin() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("$2a$10$e9pWAw3m2r5Cv/6bN/fF1.S4v.uvxu2bLOBbr80ct2hofAW9f3oq2");
        user.setEmail("admin@mail.ru");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setPhoneNumber("admin");
        user.setAddress("admin");
        user.setRole(Role.ROLE_ADMIN);
        return user;
    }

}
