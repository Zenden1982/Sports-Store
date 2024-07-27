package com.zenden.sports_store.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Classes.DTO.UserCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.UserReadDTO;
import com.zenden.sports_store.Classes.Enum.Role;
import com.zenden.sports_store.Filters.User.UserFilter;
import com.zenden.sports_store.Interfaces.TwoDtoService;
import com.zenden.sports_store.Mapper.UserMapper;
import com.zenden.sports_store.Repositories.UserRepository;

public class UserService implements TwoDtoService<UserReadDTO, UserCreateUpdateDTO, UserFilter>{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    @Override
    public UserReadDTO create(UserCreateUpdateDTO entity) {
        User user = mapper.userCreateUpdateDTOToUser(entity);
        user.setRole(Role.USER);
        return Optional.ofNullable(userRepository.save(user))
                .map(mapper::userToUserReadDTO)
                .orElseThrow(() -> new RuntimeException("Error creating user" + entity.getUsername()));
    }

    @Override
    public UserReadDTO read(Long id) {
        return mapper.userToUserReadDTO(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error reading user" + id)));
    }

    @Override
    public Page<UserReadDTO> readAll(int page, int size, String sort, UserFilter filter) {
        
    }

    @Override
    public UserReadDTO update(Long id, UserCreateUpdateDTO entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
