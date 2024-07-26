package com.zenden.sports_store.Classes.DTO;

import com.zenden.sports_store.Classes.Enum.OrderStatus;

import lombok.Data;

@Data
public class OrderCreateUpdateDTO {
    private Long id;
    private Long userId;
    private Long totalPrice;
    private OrderStatus status;
}
