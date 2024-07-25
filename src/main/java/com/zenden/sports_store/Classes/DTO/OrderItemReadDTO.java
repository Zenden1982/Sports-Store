package com.zenden.sports_store.Classes.DTO;

import lombok.Data;

@Data
public class OrderItemReadDTO {

    private Long id;
    private OrderReadDTO orderId;
    private ProductDTO productId;
    private Integer quantity;
    private Long price;
}
