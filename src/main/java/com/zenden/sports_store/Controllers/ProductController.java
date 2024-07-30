package com.zenden.sports_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zenden.sports_store.Classes.DTO.ProductCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.ProductReadDTO;
import com.zenden.sports_store.Filters.Product.ProductFilter;
import com.zenden.sports_store.Services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/products")

@Tag(name = "Product Controller", description = "CRUD операции для продуктов")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(summary = "Создать новый продукт", description = "Создает новый продукт с указанными данными и изображением")
    public ResponseEntity<ProductReadDTO> create(
            @RequestPart("image") @Parameter(description = "Изображение продукта") MultipartFile image,
            @RequestPart("entity") @Parameter(description = "Данные нового продукта") ProductCreateUpdateDTO entity) {
        entity.setImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(entity));
    }

    @PostMapping("/all")
    @Operation(summary = "Получить все продукты", description = "Возвращает список всех продуктов с возможностью пагинации и сортировки")
    public ResponseEntity<Page<ProductReadDTO>> readAll(
            @RequestParam(defaultValue = "0") @Parameter(description = "Номер страницы для пагинации") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Размер страницы для пагинации") int size,
            @RequestParam(defaultValue = "name") @Parameter(description = "Поле для сортировки продуктов") String sort,
            @RequestBody @Parameter(description = "Фильтры для получения продуктов") ProductFilter filter) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.readAll(page, size, sort, filter));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить продукт по ID", description = "Возвращает данные продукта по указанному ID")
    public ResponseEntity<ProductReadDTO> read(
            @PathVariable @Parameter(description = "ID продукта для получения") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.read(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить продукт", description = "Обновляет данные продукта по указанному ID")
    public ResponseEntity<ProductReadDTO> update(
            @PathVariable @Parameter(description = "ID продукта для обновления") Long id,
            @RequestBody @Parameter(description = "Обновленные данные продукта") ProductCreateUpdateDTO entity) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(id, entity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить продукт", description = "Удаляет продукт по указанному ID")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "ID продукта для удаления") Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
