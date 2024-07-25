package com.zenden.sports_store.Classes.DTO;

import lombok.Data;

@Data
public class OrderItemReadDTO {

    private Long id;
    private OrderReadDTO orderId;
    private ProductReadDTO productId;
    private Integer quantity;
    private Long price;
}
