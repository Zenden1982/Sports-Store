package com.zenden.sports_store.Classes.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemReadDTO {

    private Long id;
    private Integer quantity;
    private ProductReadDTO product;

}
