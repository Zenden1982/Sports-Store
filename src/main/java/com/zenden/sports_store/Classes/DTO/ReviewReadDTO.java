package com.zenden.sports_store.Classes.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReviewReadDTO {
    private long id;
    private UserReadDTO userDTO;
    private ProductReadDTO productDTO;
    private String comment;
    private long rating;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
