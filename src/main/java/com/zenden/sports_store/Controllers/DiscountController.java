package com.zenden.sports_store.Controllers;

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

import com.zenden.sports_store.Classes.DTO.DiscountCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.DiscountReadDTO;
import com.zenden.sports_store.Filters.Discount.DiscountFilter;
import com.zenden.sports_store.Services.DiscountService;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping
    public ResponseEntity<DiscountReadDTO> create(@RequestBody DiscountCreateUpdateDTO discount) { return ResponseEntity.status(201)
        .body(discountService
        .create(discount));
    }

    @PostMapping("/all")
    public ResponseEntity<Page<DiscountReadDTO>> readAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestBody DiscountFilter filter) {
        return ResponseEntity.status(200).body(discountService.readAll(page, size, sort, filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountReadDTO> read(@PathVariable Long id) {
        return ResponseEntity.status(200).body(discountService.read(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountReadDTO> update(@PathVariable Long id,
    @RequestBody DiscountCreateUpdateDTO discount) {
        return ResponseEntity.status(200).body(discountService.update(id, discount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        discountService.delete(id);
        return ResponseEntity.status(200).build();
    }
}
