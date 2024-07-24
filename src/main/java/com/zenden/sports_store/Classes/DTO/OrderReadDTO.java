package com.zenden.sports_store.Classes.DTO;

import java.time.LocalDateTime;

import com.zenden.sports_store.Classes.Enum.OrderStatus;

import lombok.Data;

@Data
public class OrderReadDTO {
    private long id;
    //private long userId;
    private long totalPrice;
    private OrderStatus status;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
