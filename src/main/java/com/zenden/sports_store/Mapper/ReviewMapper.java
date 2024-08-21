package com.zenden.sports_store.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zenden.sports_store.Classes.Product;
import com.zenden.sports_store.Classes.Review;
import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Classes.DTO.ReviewCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.ReviewReadDTO;
import com.zenden.sports_store.Repositories.ProductRepository;
import com.zenden.sports_store.Repositories.UserRepository;

@Component
@Mapper(componentModel = "spring", uses = { UserMapper.class, ProductMapper.class })
public abstract class ReviewMapper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Mapping(source = "product", target = "productReadDTO")
    @Mapping(source = "user", target = "userReadDTO")
    public abstract ReviewReadDTO reviewToReviewReadDTO(Review review);

    @Mapping(source = "userId", target = "user", qualifiedByName = "user")
    @Mapping(source = "productId", target = "product", qualifiedByName = "product")
    public abstract Review reviewCreateUpdateDTOToReview(ReviewCreateUpdateDTO reviewReadDTO);

    @Named("user")
    public User mapUserIdToUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Named("product")
    public Product mapProductIdToProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
