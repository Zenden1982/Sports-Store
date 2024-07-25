package com.zenden.sports_store.Classes.DTO;

import lombok.Data;

@Data
public class ProductReadDTO {

    private long id;
    private String productName;
    private String productDescription;
    private long price;
    private int stock;
    private CategoryDTO categoryDTO;
    private byte[] image;
}
