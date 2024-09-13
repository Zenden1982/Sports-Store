package com.zenden.sports_store.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zenden.sports_store.Classes.DTO.CartCreateDTO;
import com.zenden.sports_store.Classes.DTO.CartReadDTO;
import com.zenden.sports_store.Services.CartService;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
@Data
public class CartController {
    private final CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartReadDTO> read(@PathVariable Long id) {
        return ResponseEntity.status(200).body(cartService.read(id));
    }

    @PostMapping
    public void create(@RequestBody CartCreateDTO cart) {
        cartService.create(cart);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<CartReadDTO> getByUser(@Parameter(description = "User ID") @PathVariable Long id) {
        return ResponseEntity.status(200).body(cartService.getByUser(id));
    }

    @PostMapping("/addProduct")
    public ResponseEntity<CartReadDTO> addProduct(@RequestParam Long cartId, @RequestParam Long productId) {
        return ResponseEntity.status(201).body(cartService.addProduct(cartId, productId));
    }

    @DeleteMapping
    public ResponseEntity<CartReadDTO> removeProduct(@RequestParam Long cartId, @RequestParam Long productId) {
        return ResponseEntity.status(201).body(cartService.removeProduct(cartId, productId));
    }
}
