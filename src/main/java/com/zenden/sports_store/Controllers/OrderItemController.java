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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping("/api/orderitems")
@Tag(name = "Order Item Controller", description = "CRUD операции для элементов заказа")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    @Operation(summary = "Создать новый элемент заказа", description = "Создает новый элемент заказа с указанными данными")
    public ResponseEntity<OrderItemReadDTO> create(
            @RequestBody @Parameter(description = "Данные нового элемента заказа") OrderItemCreateUpdateDTO orderItem) {
        return ResponseEntity.status(201).body(orderItemService.create(orderItem));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить элемент заказа по ID", description = "Возвращает данные элемента заказа по указанному ID")
    public ResponseEntity<OrderItemReadDTO> read(
            @PathVariable @Parameter(description = "ID элемента заказа для получения") Long id) {
        return ResponseEntity.status(200).body(orderItemService.read(id));
    }

    @PostMapping("/all")
    @Operation(summary = "Получить все элементы заказа", description = "Возвращает список всех элементов заказа с возможностью пагинации и сортировки")
    public ResponseEntity<Page<OrderItemReadDTO>> readAll(
            @RequestParam(defaultValue = "0") @Parameter(description = "Номер страницы для пагинации") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Размер страницы для пагинации") int size,
            @RequestParam(defaultValue = "id") @Parameter(description = "Поле для сортировки элементов заказа") String sort,
            @RequestBody @Parameter(description = "Фильтры для получения элементов заказа") OrderItemFilter filter) {
        return ResponseEntity.status(200).body(orderItemService.readAll(page, size, sort, filter));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить элемент заказа", description = "Обновляет данные элемента заказа по указанному ID")
    public ResponseEntity<OrderItemReadDTO> update(
            @PathVariable @Parameter(description = "ID элемента заказа для обновления") Long id,
            @RequestBody @Parameter(description = "Обновленные данные элемента заказа") OrderItemCreateUpdateDTO orderItem) {
        return ResponseEntity.status(200).body(orderItemService.update(id, orderItem));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить элемент заказа", description = "Удаляет элемент заказа по указанному ID")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "ID элемента заказа для удаления") Long id) {
        orderItemService.delete(id);
        return ResponseEntity.status(200).build();
    }
}