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

import com.zenden.sports_store.Classes.DTO.OrderItemCreateUpdateDTO;
import com.zenden.sports_store.Classes.DTO.OrderItemReadDTO;
import com.zenden.sports_store.Filters.OrderItem.OrderItemFilter;
import com.zenden.sports_store.Services.OrderItemService;

@RestController
@CrossOrigin
@RequestMapping("/api/orderitems")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<OrderItemReadDTO> create(@RequestBody OrderItemCreateUpdateDTO orderItem) {
        return ResponseEntity.status(201).body(orderItemService.create(orderItem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemReadDTO> read(@PathVariable Long id) {
        return ResponseEntity.status(200).body(orderItemService.read(id));
    }

    @PostMapping("/all")
    public ResponseEntity<Page<OrderItemReadDTO>> readAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestBody OrderItemFilter filter) {
        return ResponseEntity.status(200).body(orderItemService.readAll(page, size, sort, filter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemReadDTO> update(@PathVariable Long id, @RequestBody OrderItemCreateUpdateDTO orderItem) {
        return ResponseEntity.status(200).body(orderItemService.update(id, orderItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderItemService.delete(id);
        return ResponseEntity.status(200).build();
    }
}
