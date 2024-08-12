package com.zenden.sports_store.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import com.zenden.sports_store.Classes.DTO.CategoryDTO;
import com.zenden.sports_store.Filters.Category.CategoryFilter;
import com.zenden.sports_store.Services.CategoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(entity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> read(@PathVariable Long id) {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.read(id));
    }

    @PostMapping("/all")
    public ResponseEntity<Page<CategoryDTO>> readAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "categoryName") String sort,
            @RequestBody CategoryFilter filter)
        {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.readAll(page, size, sort, filter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO entity) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(id, entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
