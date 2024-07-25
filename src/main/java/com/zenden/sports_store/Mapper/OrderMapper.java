package com.zenden.sports_store.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zenden.sports_store.Classes.DTO.OrderCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.OrderReadDTO;
import com.zenden.sports_store.Classes.Order;
import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Repositories.UserRepository;

@Mapper(componentModel = "spring")
@Component
public abstract class OrderMapper {

    @Autowired
    UserRepository userRepository;

    
    public abstract OrderReadDTO orderToOrderReadDTO(Order order);
    @Mapping(source="userId", target = "userId", qualifiedByName = "userId")
    public abstract Order orderCreateUpdateDTOToOrder(OrderCreateUpdateDTO orderCreateUpdateDTO);

    @Named("userId")
    public User mapUserIdToUser(long id) {
        return userRepository.findById(id).orElse(null);
    }

}
