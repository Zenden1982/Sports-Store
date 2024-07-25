package com.zenden.sports_store.Classes.DTO;

import lombok.Data;

@Data
public class OrderItemCreateUpdateDTO {
    private Long id;
    private OrderReadDTO orderReadDTO;
    private ProductDTO productDTO;
    private Integer quantity;
    private Long price;
}
