package com.zenden.sports_store.Classes.DTO;

import lombok.Data;

@Data
public class ReviewCreateUpdateDTO {
    private long id;
    private UserReadDTO userDTO;
    private String comment;
    private long rating;
}
