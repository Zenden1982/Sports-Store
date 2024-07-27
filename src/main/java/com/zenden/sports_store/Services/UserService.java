package com.zenden.sports_store.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Classes.DTO.UserCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.UserReadDTO;
import com.zenden.sports_store.Classes.Enum.Role;
import com.zenden.sports_store.Filters.User.UserFilter;
import com.zenden.sports_store.Filters.User.UserSpecification;
import com.zenden.sports_store.Interfaces.TwoDtoService;
import com.zenden.sports_store.Mapper.UserMapper;
import com.zenden.sports_store.Repositories.UserRepository;


@Component
@Service
public class UserService implements TwoDtoService<UserReadDTO, UserCreateUpdateDTO, UserFilter>{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    @Transactional
    @Override
    public UserReadDTO create(UserCreateUpdateDTO entity) {
        User user = mapper.userCreateUpdateDTOToUser(entity);
        user.setRole(Role.USER);
        return Optional.ofNullable(userRepository.save(user))
                .map(mapper::userToUserReadDTO)
                .orElseThrow(() -> new RuntimeException("Error creating user" + entity.getUsername()));
    }

    @Transactional(readOnly = true)
    @Override
    public UserReadDTO read(Long id) {
        return mapper.userToUserReadDTO(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error reading user" + id)));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserReadDTO> readAll(int page, int size, String sort, UserFilter filter) {
        Specification<User> specification = Specification.where(null);

        if (filter != null) {
            specification = specification
                .and(filter.getUsername() != null && !filter.getUsername().isEmpty()
                    ? UserSpecification.usernameLike(filter.getUsername())
                    : null)
                .and(filter.getEmail() != null && !filter.getEmail().isEmpty()
                    ? UserSpecification.emailLike(filter.getEmail())
                    : null)
                .and(filter.getRole() != null
                    ? UserSpecification.roleEquals(filter.getRole())
                    : null)
                .and(filter.getFirstName() != null && !filter.getFirstName().isEmpty()
                    ? UserSpecification.firstNameLike(filter.getFirstName())
                    : null)
                .and(filter.getLastName() != null && !filter.getLastName().isEmpty()
                    ? UserSpecification.lastNameLike(filter.getLastName())
                    : null)
                .and(filter.getPhoneNumber() != null && !filter.getPhoneNumber().isEmpty()
                    ? UserSpecification.phoneNumberLike(filter.getPhoneNumber())
                    : null)
                .and(filter.getAddress() != null && !filter.getAddress().isEmpty()
                    ? UserSpecification.addressLike(filter.getAddress())
                    : null);
        }

        try {
            return userRepository.findAll(specification, PageRequest.of(page, size))
                .map(mapper::userToUserReadDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error reading users", e);
        }
    }

    @Transactional
    @Override
    public UserReadDTO update(Long id, UserCreateUpdateDTO entity) {
        return userRepository.findById(id).map(user -> {
                user = mapper.userCreateUpdateDTOToUser(entity);
            return mapper.userToUserReadDTO(userRepository.saveAndFlush(user));
        }).orElseThrow(() -> {
            throw new RuntimeException("Error updating user" + id);
        });
    }

    @Transactional
    @Override
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleting user" + id, e);
        }
    }

}

