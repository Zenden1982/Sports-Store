package com.zenden.sports_store.Classes.DTO;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductDTO {
    private long id;
    private String productName;
    private String productDescription;
    private long price;
    private int stock;
    private long categoryId;
    private MultipartFile image;
}
