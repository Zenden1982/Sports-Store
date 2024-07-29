package com.zenden.sports_store.Filters.Order;

import com.zenden.sports_store.Classes.Enum.OrderStatus;

import lombok.Data;

@Data
public class OrderFilter {

    private Long userId;
    
    private OrderStatus status;
}
