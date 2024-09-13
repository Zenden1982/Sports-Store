package com.zenden.sports_store.Classes.DTO;

import java.util.List;

import lombok.Data;

@Data
public class CartReadDTO {

    private Long id;

    private Double totalPrice;

    private UserReadDTO user;

    private List<CartItemReadDTO> cartItems;

    // TODO Изменить ProductReadDTO на ItemCardsReadDTO
}
