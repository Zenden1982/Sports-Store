package com.zenden.sports_store.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
// @Profile("dev")
public class AdminInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private User admin;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (userRepository.findByUsername(admin.getUsername()).isEmpty()) {
            log.info("Admin user created with login: admin and password: admin");
            userRepository.save(admin);
        }

        else {
            log.info("Admin user already exists with login: admin and password: admin");
        }

    }

}
