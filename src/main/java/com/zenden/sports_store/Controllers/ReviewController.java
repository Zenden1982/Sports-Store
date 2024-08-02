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

import com.zenden.sports_store.Classes.DTO.ReviewCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.ReviewReadDTO;
import com.zenden.sports_store.Filters.Review.ReviewFilter;
import com.zenden.sports_store.Services.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewReadDTO> create(@RequestBody ReviewCreateUpdateDTO review) {
        return ResponseEntity.status(201).body(reviewService.create(review));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewReadDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.read(id));
    }

    @PostMapping("/all")
    public ResponseEntity<Page<ReviewReadDTO>> readAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestBody ReviewFilter filter) {
        return ResponseEntity.ok(reviewService.readAll(page, size, sort, filter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewReadDTO> update(@PathVariable Long id, @RequestBody ReviewCreateUpdateDTO review) {
        return ResponseEntity.status(200).body(reviewService.update(id, review));
    }
}

