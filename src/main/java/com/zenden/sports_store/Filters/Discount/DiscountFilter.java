package com.zenden.sports_store.Filters.Discount;

import lombok.Data;

@Data
public class DiscountFilter {
    private Long productId;
    private Integer percengateLess;
    private Integer percengateGreater;
}
