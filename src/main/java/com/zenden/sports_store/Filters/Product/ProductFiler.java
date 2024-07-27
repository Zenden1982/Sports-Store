package com.zenden.sports_store.Filters.Product;

import lombok.Data;

@Data
public class ProductFiler {
    private String name;
    private String description;
    private Long priceLess;
    private Long priceGreater;
    private Long categoryId;
}
