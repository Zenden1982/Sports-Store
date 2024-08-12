package com.zenden.sports_store.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zenden.sports_store.Classes.AuthRequest;
import com.zenden.sports_store.Classes.Role;
import com.zenden.sports_store.Classes.User;
import com.zenden.sports_store.Classes.DTO.UserCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.UserReadDTO;
import com.zenden.sports_store.Filters.User.UserFilter;
import com.zenden.sports_store.Filters.User.UserSpecification;
import com.zenden.sports_store.Interfaces.TwoDtoService;
import com.zenden.sports_store.Mapper.UserMapper;
import com.zenden.sports_store.Repositories.RoleRepository;
import com.zenden.sports_store.Repositories.UserRepository;
import com.zenden.sports_store.Security.JwtTokenUtils;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;


@Component
@Service
@Slf4j
public class UserService implements TwoDtoService<UserReadDTO, UserCreateUpdateDTO, UserFilter>, UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private  UserMapper mapper;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    
    @Autowired
    private EmailService mailService;
    
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Transactional
    @Override
    public UserReadDTO create(UserCreateUpdateDTO entity) {
        User user = mapper.userCreateUpdateDTOToUser(entity);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER").get();
        if (role == null) {
            role = new Role(0, "ROLE_USER");
            roleRepository.saveAndFlush(role);
        }
        
        user.setRoles(List.of(role));
        User userOpt = userRepository.save(user);
        try {
            mailService.sendMail(user.getFirstName(), user.getEmail());
        } catch (MessagingException e) {
            log.error("Error sending mail", e);
        }
        return Optional.ofNullable(userOpt)
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
            User tempUser = mapper.userCreateUpdateDTOToUser(entity);
            user.setUsername(tempUser.getUsername());
            user.setPassword(tempUser.getPassword());
            user.setEmail(tempUser.getEmail());
            user.setFirstName(tempUser.getFirstName());
            user.setLastName(tempUser.getLastName());
            user.setPhoneNumber(tempUser.getPhoneNumber());
            user.setAddress(tempUser.getAddress());
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
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username + " not found"));
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList());
    }
    
    public String generateToken(AuthRequest user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (AuthenticationException e) {
            return e.getMessage();
        }
        
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return token;
    }
    
}

