package com.zenden.sports_store.Classes.DTO;

import com.zenden.sports_store.Classes.Enum.OrderStatus;

import lombok.Data;

@Data
public class OrderCreateUpdateDTO {
    private long id;
    private long userId;
    private long totalPrice;
    private OrderStatus status;
}
