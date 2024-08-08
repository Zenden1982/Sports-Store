package com.zenden.sports_store.Controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zenden.sports_store.Classes.AuthRequest;
import com.zenden.sports_store.Classes.DTO.UserCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.UserReadDTO;
import com.zenden.sports_store.Filters.User.UserFilter;
import com.zenden.sports_store.Security.JwtTokenUtils;
import com.zenden.sports_store.Services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtils jwt;

    @PostMapping
    public ResponseEntity<UserReadDTO> create(@RequestBody UserCreateUpdateDTO user) {
        return ResponseEntity.status(201).body(userService.create(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest user) {

        return ResponseEntity.status(200).body(userService.generateToken(user));
        
    }

    @PostMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/role")
    public List<String> role(String token) {
        return jwt.getRolesFromToken(token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReadDTO> read(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.read(id));
    }

    @PostMapping("/all")
    public ResponseEntity<Page<UserReadDTO>> readAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestBody UserFilter filter) {
        return ResponseEntity.status(200).body(userService.readAll(page, size, sort, filter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserReadDTO> update(@PathVariable Long id, @RequestBody UserCreateUpdateDTO entity) {
        return ResponseEntity.status(200).body(userService.update(id, entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(200).build();
    }
}
