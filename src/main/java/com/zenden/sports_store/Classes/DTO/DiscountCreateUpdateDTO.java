package com.zenden.sports_store.Classes.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DiscountCreateUpdateDTO {
    private Long id;
    private Long productId;
    private int percentage;
    private LocalDateTime expiryDate;
}
