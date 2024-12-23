package com.zenden.sports_store.Classes.DTO;

import java.util.List;

import com.zenden.sports_store.Classes.Enum.OrderStatus;

import lombok.Data;

@Data
public class OrderCreateUpdateDTO {
    private Long id;
    private Long userId;
    private List<OrderItemCreateUpdateDTO> orderItemIds;
    private OrderStatus status;

}
