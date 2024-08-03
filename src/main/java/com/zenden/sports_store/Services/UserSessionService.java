package com.zenden.sports_store.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@SessionScope
@Service
@Data
public class UserSessionService {

    private String currency = "RUB";
}
