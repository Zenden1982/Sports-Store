package com.zenden.sports_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zenden.sports_store.Classes.DTO.UserCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.UserReadDTO;
import com.zenden.sports_store.Filters.User.UserFilter;
import com.zenden.sports_store.Services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
@Tag(name = "User Controller", description = "CRUD операции для пользователей")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Создать нового пользователя", description = "Создает нового пользователя с указанными данными")
    public ResponseEntity<UserReadDTO> create(
            @RequestBody @Parameter(description = "Данные нового пользователя") UserCreateUpdateDTO user) {
        return ResponseEntity.status(201).body(userService.create(user));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID", description = "Возвращает данные пользователя по указанному ID")
    public ResponseEntity<UserReadDTO> read(
            @PathVariable @Parameter(description = "ID пользователя для получения") Long id) {
        return ResponseEntity.status(200).body(userService.read(id));
    }

    @PostMapping("/all")
    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех пользователей с возможностью пагинации и сортировки")
    public ResponseEntity<Page<UserReadDTO>> readAll(
            @RequestParam(defaultValue = "0") @Parameter(description = "Номер страницы для пагинации") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Размер страницы для пагинации") int size,
            @RequestParam(defaultValue = "name") @Parameter(description = "Поле для сортировки пользователей") String sort,
            @RequestBody @Parameter(description = "Фильтры для получения пользователей") UserFilter filter) {
        return ResponseEntity.status(200).body(userService.readAll(page, size, sort, filter));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя", description = "Обновляет данные пользователя по указанному ID")
    public ResponseEntity<UserReadDTO> update(
            @PathVariable @Parameter(description = "ID пользователя для обновления") Long id,
            @RequestBody @Parameter(description = "Обновленные данные пользователя") UserCreateUpdateDTO entity) {
        return ResponseEntity.status(200).body(userService.update(id, entity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по указанному ID")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "ID пользователя для удаления") Long id) {
        userService.delete(id);
        return ResponseEntity.status(200).build();
    }
}
