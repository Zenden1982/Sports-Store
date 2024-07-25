package com.zenden.sports_store.Classes.DTO;

import lombok.Data;

@Data
public class ReviewCreateUpdateDTO {
    private long id;
    private long userId;
    private long productId;
    private String comment;
    private long rating;
}
