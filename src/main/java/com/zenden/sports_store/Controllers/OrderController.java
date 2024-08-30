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

import com.zenden.sports_store.Classes.DTO.OrderCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.OrderReadDTO;
import com.zenden.sports_store.Filters.Order.OrderFilter;
import com.zenden.sports_store.Services.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderReadDTO> create(@RequestBody OrderCreateUpdateDTO order) {

        return ResponseEntity.status(201).body(orderService.create(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderReadDTO> read(@PathVariable Long id) {
        return ResponseEntity.status(200).body(orderService.read(id));
    }

    @PostMapping("/all")
    public ResponseEntity<Page<OrderReadDTO>> readAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestBody OrderFilter filter) {
        return ResponseEntity.status(200).body(orderService.readAll(page, size, sort, filter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderReadDTO> update(@PathVariable Long id, @RequestBody OrderCreateUpdateDTO entity) {
        return ResponseEntity.status(200).body(orderService.update(id, entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.status(200).build();
    }

}
