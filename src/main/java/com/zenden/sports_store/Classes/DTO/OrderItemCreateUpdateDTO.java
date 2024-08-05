package com.zenden.sports_store.Classes.DTO;

import lombok.Data;

@Data
public class OrderItemCreateUpdateDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
}
