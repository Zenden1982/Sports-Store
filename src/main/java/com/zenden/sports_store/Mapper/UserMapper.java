package com.zenden.sports_store.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.zenden.sports_store.Classes.DTO.UserCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.UserReadDTO;
import com.zenden.sports_store.Classes.Role;
import com.zenden.sports_store.Classes.User;

@Mapper(componentModel = "spring")
@Component
public abstract class UserMapper {
    
    
    public abstract UserReadDTO userToUserReadDTO(User user);
    
    public abstract User userCreateUpdateDTOToUser(UserCreateUpdateDTO userCreateUpdateDTO);
    
    public List<String> getRoles(List<Role> roles) {
        return roles.stream().map(Role::getName).toList();
    }
}
