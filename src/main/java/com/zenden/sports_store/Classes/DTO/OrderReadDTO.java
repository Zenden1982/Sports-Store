package com.zenden.sports_store.Classes.DTO;

import java.time.LocalDateTime;

import com.zenden.sports_store.Classes.Enum.OrderStatus;

import lombok.Data;

@Data
public class OrderReadDTO {
    private Long id;
    private UserReadDTO userReadDTO;
    private Long totalPrice;
    private OrderStatus status;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
