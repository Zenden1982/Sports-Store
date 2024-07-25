package com.zenden.sports_store.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zenden.sports_store.Classes.Discount;
import com.zenden.sports_store.Classes.Product;
import com.zenden.sports_store.Classes.DTO.DiscountDTO;
import com.zenden.sports_store.Repositories.ProductRepository;

@Mapper(componentModel = "spring")
@Component
public abstract class DiscountMapper {

    @Autowired
    private ProductRepository productRepository;

    public abstract DiscountDTO discountToDiscountDTO(Discount discount);
    @Mapping(source = "productId", target = "product", qualifiedByName = "idToProduct")
    public abstract Discount discountDTOtoDiscount(DiscountDTO discountDTO);

    @Named("idToProduct")
    public Product idToDiscount(long id) {
        return productRepository.findById(id).orElse(null);
    }
}
