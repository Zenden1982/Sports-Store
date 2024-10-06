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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zenden.sports_store.Classes.DTO.ProductCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.ProductReadDTO;
import com.zenden.sports_store.Filters.Product.ProductFiler;
import com.zenden.sports_store.Services.ProductService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductReadDTO> create(@RequestBody ProductCreateUpdateDTO entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(entity));
    }

    @PostMapping("/all")
    @Operation(summary = "Получить все продукты", description = "В Headers можно передать два параметра Discount:true/false и Currency:USD/EUR/KZT/RUB (по умолчанию RUB)")
    public ResponseEntity<Page<ProductReadDTO>> readAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productName") String sort,
            @RequestBody ProductFiler filter,
            @RequestHeader(value = "Currency", defaultValue = "RUB") String currency) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.readAll(page, size, sort, filter, currency));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить продукт по ID", description = "В Headers можно передать два параметра Discount:true/false и Currency:USD/EUR/KZT/RUB (по умолчанию RUB)")
    public ResponseEntity<ProductReadDTO> read(@PathVariable Long id,
            @RequestHeader(value = "Currency", defaultValue = "RUB") String currency) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.read(id, currency));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductReadDTO> update(@PathVariable Long id, @RequestBody ProductCreateUpdateDTO entity) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(id, entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
