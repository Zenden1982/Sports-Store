package com.zenden.sports_store.Mapper;

import com.zenden.sports_store.Classes.Discount;
import com.zenden.sports_store.Classes.DTO.DiscountDTO;

public abstract class DiscountMapper {

    public abstract DiscountDTO discountToDiscountDTO(Discount discount);
    public abstract Discount discountDTOtoDiscount(DiscountDTO discountDTO);
}
