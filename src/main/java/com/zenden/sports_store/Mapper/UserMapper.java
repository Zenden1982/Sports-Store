package com.zenden.sports_store.Mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Classes.DTO.UserCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.UserReadDTO;

@Mapper(componentModel = "spring")
@Component
public abstract class UserMapper {


    public abstract UserReadDTO userToUserReadDTO(User user);

    public abstract User userCreateUpdateDTOToUser(UserCreateUpdateDTO userCreateUpdateDTO);
}
