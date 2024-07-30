package com.zenden.sports_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import com.zenden.sports_store.Classes.DTO.CategoryDTO;
import com.zenden.sports_store.Filters.Category.CategoryFilter;
import com.zenden.sports_store.Services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
@Tag(name = "Category Controller", description = "CRUD операции для категорий")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @PostMapping
    @Operation(summary = "Создать новую категорию", description = "Создает новую категорию с указанными данными")
    public ResponseEntity<CategoryDTO> create(@RequestBody @Parameter(description = "Данные новой категории") CategoryDTO entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(entity));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить категорию по ID", description = "Возвращает данные категории по указанному ID")
    public ResponseEntity<CategoryDTO> read(@PathVariable @Parameter(description = "ID категории для получения") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.read(id));
    }

    @PostMapping("/all")
    @Operation(summary = "Получить все категории", description = "Возвращает список всех категорий с возможностью пагинации и сортировки")
    public ResponseEntity<Page<CategoryDTO>> readAll(
            @RequestParam(defaultValue = "0") @Parameter(description = "Номер страницы для пагинации") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Размер страницы для пагинации") int size,
            @RequestParam(defaultValue = "categoryName") @Parameter(description = "Поле для сортировки категорий") String sort,
            @RequestBody @Parameter(description = "Фильтры для получения категорий") CategoryFilter filter) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.readAll(page, size, sort, filter));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить категорию", description = "Обновляет данные категории по указанному ID")
    public ResponseEntity<CategoryDTO> update(@PathVariable @Parameter(description = "ID категории для обновления") Long id,
                                              @RequestBody @Parameter(description = "Обновленные данные категории") CategoryDTO entity) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(id, entity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить категорию", description = "Удаляет категорию по указанному ID")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "ID категории для удаления") Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}