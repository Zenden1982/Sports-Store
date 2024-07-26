package com.zenden.sports_store.Classes.DTO;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductCreateUpdateDTO {
    private Long id;
    private String productName;
    private String productDescription;
    private Long price;
    private int stock;
    private Long categoryId;
    private MultipartFile image;
}
