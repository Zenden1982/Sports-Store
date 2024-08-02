package com.zenden.sports_store.Classes.DTO;

import lombok.Data;

@Data
public class ProductReadDTO {

    private Long id;
    private String productName;
    private String productDescription;
    private Long price;
    private int stock;
    private CategoryDTO categoryDTO;
}
