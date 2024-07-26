package com.zenden.sports_store.Classes.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReviewReadDTO {
    private Long id;
    private UserReadDTO userReadDTO;
    private ProductReadDTO productReadDTO;
    private String comment;
    private Long rating;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
