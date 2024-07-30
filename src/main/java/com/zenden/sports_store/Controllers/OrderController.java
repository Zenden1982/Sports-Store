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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/orders")

@Tag(name = "Order Controller", description = "CRUD операции для заказов")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @Operation(summary = "Создать новый заказ", description = "Создает новый заказ с указанными данными")
    public ResponseEntity<OrderReadDTO> create(
            @RequestBody @Parameter(description = "Данные нового заказа") OrderCreateUpdateDTO order) {
        return ResponseEntity.status(201).body(orderService.create(order));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить заказ по ID", description = "Возвращает данные заказа по указанному ID")
    public ResponseEntity<OrderReadDTO> read(
            @PathVariable @Parameter(description = "ID заказа для получения") Long id) {
        return ResponseEntity.status(200).body(orderService.read(id));
    }

    @PostMapping("/all")
    @Operation(summary = "Получить все заказы", description = "Возвращает список всех заказов с возможностью пагинации и сортировки")
    public ResponseEntity<Page<OrderReadDTO>> readAll(
            @RequestParam(defaultValue = "0") @Parameter(description = "Номер страницы для пагинации") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Размер страницы для пагинации") int size,
            @RequestParam(defaultValue = "id") @Parameter(description = "Поле для сортировки заказов") String sort,
            @RequestBody @Parameter(description = "Фильтры для получения заказов") OrderFilter filter) {
        return ResponseEntity.status(200).body(orderService.readAll(page, size, sort, filter));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить заказ", description = "Обновляет данные заказа по указанному ID")
    public ResponseEntity<OrderReadDTO> update(
            @PathVariable @Parameter(description = "ID заказа для обновления") Long id,
            @RequestBody @Parameter(description = "Обновленные данные заказа") OrderCreateUpdateDTO entity) {
        return ResponseEntity.status(200).body(orderService.update(id, entity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить заказ", description = "Удаляет заказ по указанному ID")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "ID заказа для удаления") Long id) {
        orderService.delete(id);
        return ResponseEntity.status(200).build();
    }

}
