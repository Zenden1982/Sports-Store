package com.zenden.sports_store.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zenden.sports_store.Classes.DTO.OrderCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.OrderItemCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.OrderReadDTO;
import com.zenden.sports_store.Classes.Order;
import com.zenden.sports_store.Classes.Product;
import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Repositories.ProductRepository;
import com.zenden.sports_store.Repositories.UserRepository;

@Mapper(componentModel = "spring", uses = { UserMapper.class, OrderItemMapper.class })
@Component
public abstract class OrderMapper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    public ProductRepository productRepository;

    @Mapping(source = "user", target = "userReadDTO")
    public abstract OrderReadDTO orderToOrderReadDTO(Order order);

    @Mapping(source = "orderItemIds", target = "totalPrice", qualifiedByName = "totalPrice")
    @Mapping(source = "userId", target = "user", qualifiedByName = "userId")
    public abstract Order orderCreateUpdateDTOToOrder(OrderCreateUpdateDTO orderCreateUpdateDTO);

    @Named("userId")
    public User mapUserIdToUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Named("totalPrice")
    public Double mapTotalPrice(List<OrderItemCreateUpdateDTO> orderItemIds) {
        Double total = 0.0;
        for (OrderItemCreateUpdateDTO id : orderItemIds) {
            Product product = productRepository.findById(id.getProductId()).get();
            total += product.getPrice() * id.getQuantity();
        }
        return total;
    }

}
