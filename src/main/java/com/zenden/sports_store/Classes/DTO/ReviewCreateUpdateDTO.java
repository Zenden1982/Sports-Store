package com.zenden.sports_store.Classes.DTO;

import lombok.Data;

@Data
public class ReviewCreateUpdateDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private String comment;
    private Long rating;
}
