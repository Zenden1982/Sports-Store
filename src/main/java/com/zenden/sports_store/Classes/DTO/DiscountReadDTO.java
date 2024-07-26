package com.zenden.sports_store.Classes.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DiscountReadDTO {
    private Long id;
    private String code;
    private ProductReadDTO productReadDTO;
    private int percentage;
    private LocalDateTime expiryDate;
    private String description;
    
}
