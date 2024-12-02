package com.zenden.sports_store.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.zenden.sports_store.Classes.Cart;
import com.zenden.sports_store.Classes.DTO.CartCreateDTO;
import com.zenden.sports_store.Classes.DTO.CartReadDTO;
import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Repositories.CartItemRepository;
import com.zenden.sports_store.Repositories.UserRepository;
import com.zenden.sports_store.Services.ProductService;

@Mapper(componentModel = "spring", uses = { UserMapper.class, CartItemMapper.class })
public abstract class CartMapper {

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    UserRepository userRepository;

    public abstract CartReadDTO cartToCartReadDTO(Cart cart);

    @Mapping(source = "userId", target = "user", qualifiedByName = "user")
    public abstract Cart cartCreateDTOtoCart(CartCreateDTO cartCreateDTO);

    // @Named("products")
    // public List<ProductReadDTO> products(List<CartItem> cartItems) {
    // if (cartItems != null) {
    // return cartItems.stream().map(cartItem -> {
    // return productMapper.productToProductReadDTO(cartItem.getProduct());
    // }).toList();
    // } else {
    // return null;
    // }
    // }

    @Named("user")
    public User mapUserIdToUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
