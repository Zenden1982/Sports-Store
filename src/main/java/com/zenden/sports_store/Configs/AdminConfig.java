package com.zenden.sports_store.Configs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zenden.sports_store.Classes.Role;
import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Repositories.RoleRepository;

@Configuration
public class AdminConfig {

    @Autowired
    private RoleRepository roleRepository;

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
        Role role = new Role();
        if (!roleRepository.findByName("ROLE_ADMIN").isPresent()) {

            role = new Role(0, "ROLE_ADMIN");
            roleRepository.saveAndFlush(role);
        } else {
            role = roleRepository.findByName("ROLE_ADMIN").get();
        }
        user.setRoles(List.of(role));
        return user;
    }

}
