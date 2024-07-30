package com.zenden.sports_store.Filters.Review;

import lombok.Data;

@Data
public class ReviewFilter {

    private Long productId;
    private Long userId;
    private Integer ratingLess;
    private Integer ratingGreater;
}
