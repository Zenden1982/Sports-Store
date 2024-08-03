package com.zenden.sports_store.Classes.DTO;

import lombok.Data;

@Data
public class ProductCreateUpdateDTO {
    private Long id;
    private String productName;
    private String productDescription;
    private Double price;
    private int stock;
    private Long categoryId;
}
