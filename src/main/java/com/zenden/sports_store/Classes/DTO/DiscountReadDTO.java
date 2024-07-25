package com.zenden.sports_store.Classes.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DiscountReadDTO {
    private long id;
    private String code;
    private ProductReadDTO productDTO;
    private int percentage;
    private LocalDateTime expiryDate;
    private String description;
    
}
