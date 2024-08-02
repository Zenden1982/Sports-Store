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
import com.zenden.sports_store.Filters.Product.ProductFiler;
import com.zenden.sports_store.Services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductReadDTO> create(@RequestPart("image") MultipartFile image, @RequestPart("entity") ProductCreateUpdateDTO entity) {
        entity.setImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(entity));
    }

    @PostMapping("/all")
    public ResponseEntity<Page<ProductReadDTO>> readAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestBody ProductFiler filter) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.readAll(page, size, sort, filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReadDTO> read(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.read(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductReadDTO> update(@PathVariable Long id, @RequestBody ProductCreateUpdateDTO entity) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(id, entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
