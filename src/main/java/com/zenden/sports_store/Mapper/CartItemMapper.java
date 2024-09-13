package com.zenden.sports_store.Mapper;

import org.mapstruct.Mapper;

import com.zenden.sports_store.Classes.CartItem;
import com.zenden.sports_store.Classes.DTO.CartItemReadDTO;

@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public abstract class CartItemMapper {

    public abstract CartItemReadDTO cartItemToCartItemReadDTO(CartItem cartItem);
}
