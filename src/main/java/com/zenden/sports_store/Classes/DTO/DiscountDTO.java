package com.zenden.sports_store.Classes.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DiscountDTO {
    private long id;
    private String code;
    private int percentage;
    private LocalDateTime expiryDate;
    private String description;
    
}
